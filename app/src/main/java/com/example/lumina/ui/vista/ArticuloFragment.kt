package com.example.lumina.ui.vista

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lumina.UserViewModel
import com.example.lumina.controllers.ctr_Comentario
import com.example.lumina.databinding.FragmentArticuloBinding
import com.example.lumina.models.Comentario
import com.example.lumina.models.MovieRepository
import com.example.lumina.ui.ItemsRecyclerView.ComentarioAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ArticuloFragment : Fragment() {

    private var _binding: FragmentArticuloBinding? = null
    private val binding get() = _binding!!

    private lateinit var comentarioAdapter: ComentarioAdapter
    private lateinit var comentarioController: ctr_Comentario
    private lateinit var comentariosRef: DatabaseReference
    private lateinit var userViewModel: UserViewModel // ViewModel para el usuario
    private var idPelicula: Int = 0 // Variable para almacenar idPelicula

    override fun onAttach(context: Context) {
        super.onAttach(context)
        comentarioController = ctr_Comentario()
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticuloBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        comentariosRef = FirebaseDatabase.getInstance().getReference("comentarios")

        val args = ArticuloFragmentArgs.fromBundle(requireArguments())
        idPelicula = args.id // Asignar idPelicula

        val movie = MovieRepository.getMovies().find { it.id == idPelicula }

        if (movie != null) {
            binding.imageView.setImageResource(movie.imageResId)
            binding.textViewTitulo.text = movie.title
            binding.textViewDescripcion.text = movie.description

            // Obtener el email del usuario actual
            val userEmail = userViewModel.userEmail.value ?: "usuario@example.com"

            // Configurar RecyclerView y adaptador
            comentarioAdapter = ComentarioAdapter(emptyList(), userEmail, this)
            binding.recyclerViewComentarios.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = comentarioAdapter
            }

            consultarComentarios(idPelicula)
        }

        binding.btnEnviarComentario.setOnClickListener {
            val comentarioText = binding.editTextComentario.text.toString().trim()

            if (comentarioText.isNotEmpty()) {
                // Obtener nombre de usuario y correo electrónico del ViewModel
                val nombreUsuario = userViewModel.userName.value ?: "Usuario Anónimo"
                val correoUsuario = userViewModel.userEmail.value ?: "usuario@example.com"

                // Generar un ID único para el comentario
                val nuevoComentarioId = comentariosRef.push().key?.hashCode() ?: 0

                // Crear objeto Comentario
                val comentario = Comentario(
                    idComentario = nuevoComentarioId,
                    correoUsuario = correoUsuario,
                    nombreUsuario = nombreUsuario,
                    idPelicula = idPelicula,
                    comentario = comentarioText
                )

                // Llamar a la función para registrar el comentario
                altaComentario(comentario)
                binding.editTextComentario.setText("")
                Toast.makeText(requireContext(), "Comentario publicado exitosamente", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireContext(), "El comentario no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun consultarComentarios(idPelicula: Int) {
        comentarioController.consultarComentarios(idPelicula, object : ctr_Comentario.ComentariosCallback {
            override fun onSuccess(comentariosList: List<Comentario>) {
                comentarioAdapter.actualizarListaComentarios(comentariosList)
            }

            override fun onError(errorMessage: String) {
                Toast.makeText(requireContext(), "Error al cargar comentarios: $errorMessage", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun altaComentario(comentario: Comentario) {
        comentarioController.altaComentario(comentario)
        actualizarComentarios(comentario.idPelicula)
    }

    fun actualizarComentarios(idPelicula: Int) {
        consultarComentarios(idPelicula)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun eliminarComentario(idComentario: Long) {
        comentarioController.bajaComentario(idComentario, requireContext()) { success ->
            if (success) {
                actualizarComentarios(idPelicula)
            }
        }
    }

    fun modificarComentario(idComentario: Long, nuevoComentario: String) {
        comentarioController.modificarComentario(idComentario, nuevoComentario, requireContext()) { success ->
            if (success) {
                actualizarComentarios(idPelicula)
                Toast.makeText(context, "Comentario modificado correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
