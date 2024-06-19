package com.example.lumina.models

data class Pelicula(
    val id: Int,
    val tipo: Int,
    val title: String,
    val description: String,
    val imageResId: Int,
    val rating: Float
)