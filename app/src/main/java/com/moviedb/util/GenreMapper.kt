package com.moviedb.util

import com.moviedb.network.TMDbGenre
import com.moviedb.persistence.Genre

fun List<TMDbGenre>.toDatabase() : List<Genre>{
    return map {
        Genre(id = it.id,
            name = it.name
        )
    }
}