package com.example.lumina.ui.inicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lumina.databinding.FragmentInicioBinding
import com.example.lumina.models.MovieRepository
import com.example.lumina.ui.ItemsRecyclerView.ItemVertical

class InicioFragment : Fragment() {

    private var _binding: FragmentInicioBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Obtén la lista de películas del repositorio
        val allMovies = MovieRepository.getMovies()

        // Filtra las películas para obtener solo aquellas cuyo tipo sea 0
        val filteredMoviesType0 = allMovies.filter { it.tipo == 0 }

        // Configurar el RecyclerView para las películas tipo 0 con orientación horizontal
        val recyclerViewType0 = binding.recyclerViewVertical
        recyclerViewType0.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Crear y configurar el adaptador con las películas tipo 0 filtradas
        val adapterType0 = ItemVertical(filteredMoviesType0)
        adapterType0.setOnItemClickListener { movie ->
            // Aquí puedes realizar cualquier acción que desees al hacer clic en una película
            // Por ejemplo, puedes navegar a otro fragmento pasando el ID de la película
            val action = InicioFragmentDirections.actionNavInicioToNavArticulo(movie.id)
            requireView().findNavController().navigate(action)
        }
        recyclerViewType0.adapter = adapterType0

        // Filtra las películas para obtener solo aquellas cuyo tipo sea 1
        val filteredMoviesType1 = allMovies.filter { it.tipo == 1 }

        // Configurar el RecyclerView para las películas tipo 1 con orientación horizontal
        val recyclerViewType1 = binding.recyclerViewHorizontalSeries
        recyclerViewType1.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Crear y configurar el adaptador con las películas tipo 1 filtradas
        val adapterType1 = ItemVertical(filteredMoviesType1)
        adapterType1.setOnItemClickListener { movie ->
            // Aquí puedes realizar cualquier acción que desees al hacer clic en una película
            // Por ejemplo, puedes navegar a otro fragmento pasando el ID de la película
            val action = InicioFragmentDirections.actionNavInicioToNavArticulo(movie.id)
            requireView().findNavController().navigate(action)
        }
        recyclerViewType1.adapter = adapterType1

        // Filtra las películas para obtener solo aquellas cuyo tipo sea 2
        val filteredMoviesType2 = allMovies.filter { it.tipo == 2 }

        // Configurar el RecyclerView para las películas tipo 2 con orientación horizontal
        val recyclerViewType2 = binding.recyclerViewHorizontalTrailers
        recyclerViewType2.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Crear y configurar el adaptador con las películas tipo 2 filtradas
        val adapterType2 = ItemVertical(filteredMoviesType2)
        adapterType2.setOnItemClickListener { movie ->
            // Aquí puedes realizar cualquier acción que desees al hacer clic en una película
            // Por ejemplo, puedes navegar a otro fragmento pasando el ID de la película
            val action = InicioFragmentDirections.actionNavInicioToNavArticulo(movie.id)
            requireView().findNavController().navigate(action)
        }
        recyclerViewType2.adapter = adapterType2

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
