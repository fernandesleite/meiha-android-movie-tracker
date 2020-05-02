package com.moviedb.movieList

import androidx.lifecycle.LiveData
import com.moviedb.network.TMDbApi
import com.moviedb.persistence.Genre
import com.moviedb.persistence.MoviesAppDatabase
import com.moviedb.util.toDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GenreRepository(private val database: MoviesAppDatabase) {
    suspend fun refreshGenresOfflineCache() {
        withContext(Dispatchers.IO) {
            val getGenreListSuspended = TMDbApi.retrofitService.getGenreList()
            database.genreDao.insertAll(getGenreListSuspended.genres.toDatabase())
        }
    }

    val genres: LiveData<List<Genre>> = database.genreDao.getAllGenres()
}