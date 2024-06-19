package com.example.lumina.models

import com.example.lumina.R

// MovieRepository.kt
object MovieRepository {
    private val movies = mutableListOf<Pelicula>()

    init {
        movies.addAll(
            listOf(
                Pelicula(0, 0, "Sin novedad en el frente", "La triste historia de 3 jovenes que deciden elistarse a la guerra", R.drawable.portada_sinnovedad, 4.0f),
                Pelicula(1, 0, "Spiderman", "Un brillante estudiante decide enfrentar al crimen despues de obtener poderes al ser picado por una araña.", R.drawable.portada_spiderman, 4.0f),
                Pelicula(2, 0, "Chicuarotes", "Dos jóvenes intentan huir de las situaciones de abuso, violencia y abandono que viven en su pueblo natal.", R.drawable.portada_chicuarotes, 5.0f),
                Pelicula(3, 0, "El Camino", "Jesse debe aceptar su pasado si quiere construir un futuro. Una película que sigue el final de la serie Breaking Bad.", R.drawable.portada_elcamino, 4.0f),
                Pelicula(4, 0, "El Infierno", "Benjamín García se involucra en el negocio del narcotráfico después de ser deportado de los Estados Unidos.", R.drawable.portada_elinfierno, 5.0f),

                Pelicula(5, 1, "Doctor House", "Una travesía médica donde el genio y la rebeldía del Dr. House desafían tanto las enfermedades.", R.drawable.portada_drhouse, 5.0f),
                Pelicula(6, 1, "Breaking Bad", "El descenso a la oscuridad de un profesor de quimica con cancér.", R.drawable.portada_breakingbad, 5.0f),
                Pelicula(7, 1, "Better Call Saul", "El ascenso de un abogado astuto, desde su inicio como Jimmy McGill hasta convertirse en Saul Goodman.", R.drawable.portada_bettercallsaul, 4f),
                Pelicula(8, 1, "Spartacus", "Una épica de rebelión y venganza, donde el gladiador Spartacus lidera una lucha desgarradora por la libertad.", R.drawable.portada_spartacus, 4f),
                Pelicula(9, 1, "Death Note", "Un duelo intelectual entre un inteligente detective y un misterioso asesino con complejo de dios.", R.drawable.portada_deathnote, 4f),

                Pelicula(10, 2, "Chubay Into Beffo Verse", "Narra la historia de Chubay y las huellas que dejaron en ella los Beffos", R.drawable.portada_beffoverse, 4f),
                Pelicula(11, 2, "El Ultimo Concierto de Aramis", "Aramis un talentoso artista decide dar un ultimo concierto antes de morir", R.drawable.portada_aramis, 5f),
                Pelicula(12, 2, "Rotten Pays", "La cautivadora historia de la trayectoria de una banda de Rock sin talento", R.drawable.portada_rottenpays, 3f),
                Pelicula(13, 2, "La Noche de Quique", "El espíritu de Quique, un verdadero huevón que odiaba chambear, regresa del más allá en para vengarse después de ser forzado a chambear.", R.drawable.portada_quique, 5.0f),
                Pelicula(14, 2, "Cholo Coders", "Dos amigos, verdaderos cholos, desatan su talento informático en un camino los lleva fuera del vecindario.", R.drawable.portada_cholocoders, 4f),
            )
        )
    }

    fun getMovies(): List<Pelicula> {
        return movies
    }

    fun addMovie(movie: Pelicula) {
        movies.add(movie)
    }
}
