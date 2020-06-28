package com.moviedb.util

import com.moviedb.network.TMDbGenre
import com.moviedb.network.TMDbMovie
import com.moviedb.persistence.Genre
import com.moviedb.persistence.Movie
import com.moviedb.persistence.MoviesAppDatabase

fun List<TMDbMovie>.toDatabase(database: MoviesAppDatabase): List<Movie> {
    return map {
        Movie(
            poster_path = it.poster_path,
            adult = it.adult,
            overview = it.overview,
            release_date = it.release_date,
            genre_ids = it.genre_ids?.map { id -> database.genreDao.getGenre(id).name },
            id = it.id,
            original_title = it.original_title,
            title = it.title,
            backdrop_path = it.backdrop_path,
            popularity = it.popularity,
            vote_count = it.vote_count,
            video = it.video,
            vote_average = it.vote_average
        )
    }
}


