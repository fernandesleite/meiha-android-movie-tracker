package com.moviedb.repositories

import androidx.lifecycle.LiveData
import com.moviedb.network.TMDbApi
import com.moviedb.persistence.Genre
import com.moviedb.persistence.MoviesAppDatabase
import com.moviedb.util.toDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GenreRepository(private val database: MoviesAppDatabase) {
    suspend fun refreshGenresOfflineCache(language: String) {
        withContext(Dispatchers.IO) {
            val getGenreListSuspended = TMDbApi.retrofitService.getGenreList(language)
            database.genreDao.insertAll(getGenreListSuspended.genres.toDatabase())
        }
    }

    val genres: LiveData<List<Genre>> = database.genreDao.getAllGenres()
}