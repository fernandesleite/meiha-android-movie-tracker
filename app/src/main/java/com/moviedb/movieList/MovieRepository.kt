package com.moviedb.movieList

import androidx.lifecycle.LiveData
import com.moviedb.network.TMDbApi
import com.moviedb.util.toDatabase
import com.moviedb.persistence.Movie
import com.moviedb.persistence.MoviesAppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val database: MoviesAppDatabase) {
    suspend fun refreshMoviesOfflineCache() {
        withContext(Dispatchers.IO) {
            database.movieDao.clear()
            val getMovieListSuspended = TMDbApi.retrofitService.getPopularMovies()
            database.movieDao.insertAll(getMovieListSuspended.results.toDatabase(database))
        }
    }
    val movies: LiveData<List<Movie>> = database.movieDao.getAllMovies()

    fun getMovieDetails(id: Int): LiveData<Movie> {
        return database.movieDao.getMovie(id)
    }
}