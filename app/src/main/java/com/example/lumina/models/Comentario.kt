package com.example.lumina.models

data class Comentario(
    val idComentario: Int, // Asegúrate de que idComentario sea String si Firebase lo devuelve como String
    val correoUsuario: String,
    val nombreUsuario: String,
    val idPelicula: Int,
    val comentario: String
)