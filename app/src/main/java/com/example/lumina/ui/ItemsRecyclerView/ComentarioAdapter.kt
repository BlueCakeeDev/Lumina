package com.example.lumina.ui.ItemsRecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.lumina.R
import com.example.lumina.models.Comentario
import com.example.lumina.ui.vista.ArticuloFragment

class ComentarioAdapter(
    private var comentariosList: List<Comentario>,
    private val userEmail: String, // Email del usuario actual
    private val fragment: ArticuloFragment // Referencia al Fragment
) : RecyclerView.Adapter<ComentarioAdapter.ComentarioViewHolder>() {

    inner class ComentarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txvNombreUsuario: TextView = itemView.findViewById(R.id.txv_nombreUsuario)
        private val txvComentario: TextView = itemView.findViewById(R.id.txv_comentario)
        private val btnEdit: ImageButton = itemView.findViewById(R.id.btn_edit)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btn_delete)

        private var estadoModificar: Boolean = false // Estado de modo edición
        private var comentarioOriginal: String = "" // Comentario original antes de la modificación
        private var idComentario: Long = 0 // ID del comentario

        init {
            btnEdit.setOnClickListener {
                if (estadoModificar) {
                    // Confirmar modificación
                    val nuevoComentario = txvComentario.text.toString().trim()
                    fragment.modificarComentario(idComentario, nuevoComentario)

                    estadoModificar = false
                    actualizarEstadoEditar()
                } else {
                    // Activar modo edición
                    estadoModificar = true
                    comentarioOriginal = txvComentario.text.toString() // Guardar comentario original
                    actualizarEstadoEditar()
                    txvComentario.isEnabled = true
                    txvComentario.requestFocus() // Foco en el EditText para edición
                }
            }

            btnDelete.setOnClickListener {
                if (estadoModificar) {
                    // Cancelar modificación
                    estadoModificar = false
                    actualizarEstadoEditar()
                    txvComentario.isEnabled = false
                    // Restaurar comentario original
                    txvComentario.text = comentarioOriginal
                } else {
                    // Eliminar comentario
                    fragment.eliminarComentario(idComentario)
                }
            }
        }

        fun bind(comentario: Comentario) {
            idComentario = comentario.idComentario.toLong() // Asignar el ID del comentario
            txvNombreUsuario.text = comentario.nombreUsuario
            txvComentario.text = comentario.comentario

            // Deshabilitar el TextView txv_comentario
            txvComentario.isEnabled = false

            // Verificar si el comentario pertenece al usuario actual
            if (comentario.correoUsuario == userEmail) {
                // Usuario actual, permitir editar y eliminar
                btnEdit.isEnabled = true
                btnEdit.isVisible = true
                btnDelete.isEnabled = true
                btnDelete.isVisible = true

                // Configurar botón Editar
                actualizarEstadoEditar()

            } else {
                // No es el usuario actual, bloquear editar y eliminar
                btnEdit.isEnabled = false
                btnEdit.isVisible = false
                btnDelete.isEnabled = false
                btnDelete.isVisible = false
            }
        }

        // Método para actualizar la apariencia de los botones según el estado de modificación
        private fun actualizarEstadoEditar() {
            if (estadoModificar) {
                // Modo edición activado
                btnEdit.setImageResource(R.drawable.baseline_check_circle_outline_24)
                btnDelete.setImageResource(R.drawable.baseline_cancel_24)
            } else {
                // Modo edición desactivado
                btnEdit.setImageResource(R.drawable.baseline_edit_24)
                btnDelete.setImageResource(R.drawable.baseline_delete_24)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comentario, parent, false)
        return ComentarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComentarioViewHolder, position: Int) {
        val comentario = comentariosList[position]
        holder.bind(comentario)
    }

    override fun getItemCount(): Int {
        return comentariosList.size
    }

    fun actualizarListaComentarios(nuevaLista: List<Comentario>) {
        comentariosList = nuevaLista
        notifyDataSetChanged()
    }
}
