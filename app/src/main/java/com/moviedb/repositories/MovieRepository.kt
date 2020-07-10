package com.moviedb.repositories

import com.moviedb.network.TMDbApi
import com.moviedb.network.TMDbMovieCredits
import com.moviedb.network.TMDbMovieDetails
import com.moviedb.persistence.Movie
import com.moviedb.persistence.MoviesAppDatabase
import com.moviedb.util.toDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val database: MoviesAppDatabase) {

    suspend fun refreshMoviesOfflineCache() {
        withContext(Dispatchers.IO) {
            database.movieDao.clear()
            val getMovieListSuspended = TMDbApi.retrofitService.getPopularMovies(1)
            database.movieDao.insertAll(getMovieListSuspended.results.toDatabase(database))
        }
    }

    suspend fun getPopularMovies(page: Int): List<Movie> =
        withContext(Dispatchers.IO) {
            TMDbApi.retrofitService.getPopularMovies(page).results.toDatabase(database)
        }

    suspend fun getMovieDetails(movieId: Int): TMDbMovieDetails =
        TMDbApi.retrofitService.getMovieDetails(movieId)

    suspend fun getMovieCredits(movieId: Int): TMDbMovieCredits =
        TMDbApi.retrofitService.getMovieCredits(movieId)

    suspend fun getMovieRecommendations(movieId: Int): List<Movie> =
        withContext(Dispatchers.IO) {
            TMDbApi.retrofitService.getMovieRecommendations(movieId).results.toDatabase(database)
        }
}