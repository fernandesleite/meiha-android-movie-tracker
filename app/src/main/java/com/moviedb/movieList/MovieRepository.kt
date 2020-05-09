package com.moviedb.movieList

import androidx.lifecycle.LiveData
import com.moviedb.network.*
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

    suspend fun getPopularMovies(): TMDbMoviesResponse = TMDbApi.retrofitService.getPopularMovies()

    suspend fun getMovieDetails(movieId: Int): TMDbMovieDetails =
        TMDbApi.retrofitService.getMovieDetails(movieId)

    suspend fun getMovieCredits(movieId: Int): TMDbMovieCredits =
        TMDbApi.retrofitService.getMovieCredits(movieId)

    suspend fun getMovieRecommendations(movieId: Int): TMDbMovieRecommendations =
        TMDbApi.retrofitService.getMovieRecommendations(movieId)

    val movies: LiveData<List<Movie>> = database.movieDao.getAllMovies()
}