package com.example.lumina.controllers

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.lumina.models.Comentario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ctr_Comentario {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val comentariosRef: DatabaseReference = database.getReference("comentarios")

    fun altaComentario(comentario: Comentario) {
        val database = FirebaseDatabase.getInstance()
        val comentariosRef = database.getReference("comentarios")

        // Generar un ID único para el nuevo comentario
        val nuevoComentarioId = comentariosRef.push().key

        if (nuevoComentarioId != null) {
            comentariosRef.child(nuevoComentarioId).setValue(comentario)
                .addOnSuccessListener {
                    // Comentario agregado exitosamente
                }
                .addOnFailureListener { e ->
                    // Error al agregar comentario
                }
        } else {
        }
    }

    fun bajaComentario(idComentario: Long, context: Context, callback: (Boolean) -> Unit) {
        val comentariosRef = FirebaseDatabase.getInstance().getReference("comentarios")
        val query = comentariosRef.orderByChild("idComentario").equalTo(idComentario.toDouble())

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        // Obtener la referencia al comentario específico que deseas eliminar
                        val comentarioKey = snapshot.key
                        val comentarioRef = comentariosRef.child(comentarioKey!!)

                        // Eliminar el comentario de la base de datos
                        comentarioRef.removeValue()
                            .addOnSuccessListener {
                                Toast.makeText(context, "Comentario Eliminado", Toast.LENGTH_SHORT).show()
                                callback(true)
                            }
                            .addOnFailureListener { exception ->
                                Toast.makeText(context, "Error al eliminar comentario: ${exception.message}", Toast.LENGTH_SHORT).show()
                                Log.e("bajaComentario", "Error al eliminar comentario", exception)
                                callback(false)
                            }
                    }
                } else {
                    Toast.makeText(context, "No se encontró el comentario con ID: $idComentario", Toast.LENGTH_SHORT).show()
                    callback(false)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, "Error en la consulta: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                Log.e("bajaComentario", "Error en la consulta", databaseError.toException())
                callback(false)
            }
        })
    }

    fun modificarComentario(idComentario: Long, nuevoComentario: String, context: Context, callback: (Boolean) -> Unit) {
        val comentariosRef = FirebaseDatabase.getInstance().getReference("comentarios")
        val query = comentariosRef.orderByChild("idComentario").equalTo(idComentario.toDouble())

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        // Obtener la referencia al comentario específico que deseas modificar
                        val comentarioKey = snapshot.key
                        val comentarioRef = comentariosRef.child(comentarioKey!!)

                        // Actualizar el campo "comentario" del comentario en la base de datos
                        comentarioRef.child("comentario").setValue(nuevoComentario)
                            .addOnSuccessListener {
                                callback(true)
                                Toast.makeText(context, "Comentario modificado correctamente", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { exception ->
                                callback(false)
                                Log.e("modificarComentario", "Error al modificar comentario", exception)
                                Toast.makeText(context, "Error al modificar comentario: ${exception.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    callback(false)
                    Log.e("modificarComentario", "No se encontró el comentario con ID: $idComentario")
                    Toast.makeText(context, "No se encontró el comentario con ID: $idComentario", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                callback(false)
                Log.e("modificarComentario", "Error en la consulta: ${databaseError.message}", databaseError.toException())
                Toast.makeText(context, "Error en la consulta: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    fun consultarComentarios(idPelicula: Int, callback: ComentariosCallback) {
        comentariosRef.orderByChild("idPelicula").equalTo(idPelicula.toDouble())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val comentariosList = mutableListOf<Comentario>()
                    for (comentarioSnapshot in snapshot.children) {
                        // Asegúrate de que idComentario esté almacenado como Long en Firebase
                        val idComentario = comentarioSnapshot.child("idComentario").getValue(Long::class.java)
                        val correoUsuario = comentarioSnapshot.child("correoUsuario").getValue(String::class.java)
                        val nombreUsuario = comentarioSnapshot.child("nombreUsuario").getValue(String::class.java)
                        val idPelicula = comentarioSnapshot.child("idPelicula").getValue(Int::class.java)
                        val comentario = comentarioSnapshot.child("comentario").getValue(String::class.java)

                        if (idComentario != null && correoUsuario != null && nombreUsuario != null && idPelicula != null && comentario != null) {
                            val comentarioObj = Comentario(
                                idComentario.toInt(), // Asegúrate de convertir Long a Int si es necesario
                                correoUsuario,
                                nombreUsuario,
                                idPelicula,
                                comentario
                            )
                            comentariosList.add(comentarioObj)
                        }
                    }
                    callback.onSuccess(comentariosList)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback.onError(error.message)
                }
            })
    }

    interface ComentariosCallback {
        fun onSuccess(comentariosList: List<Comentario>)
        fun onError(errorMessage: String)
    }
}
