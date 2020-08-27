package com.moviedb.repositories

import androidx.lifecycle.LiveData
import com.moviedb.network.TMDbApi
import com.moviedb.network.TMDbMovieCredits
import com.moviedb.network.TMDbMovieDetails
import com.moviedb.persistence.Movie
import com.moviedb.persistence.MovieStatus
import com.moviedb.persistence.MoviesAppDatabase
import com.moviedb.util.toDatabase
import com.moviedb.util.toMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val database: MoviesAppDatabase) {

    suspend fun refreshMoviesOfflineCache() {
        withContext(Dispatchers.IO) {
            database.movieStatusDao.insertAll(MovieStatus.populateData())
        }
    }

    suspend fun movieToCache(status: Int, movieId: Int) {
        withContext(Dispatchers.IO) {
            val getMovieListSuspended = TMDbApi.retrofitService.getMovieDetails(movieId)
            val movie = getMovieListSuspended.toMovie(database)
            movie.category = status
            database.movieDao.insert(movie)
        }
    }

    fun getMovie(movieId: Int): LiveData<Int> {
        return database.movieDao.getMovie(movieId)
    }

    fun getWatchedMovies(): LiveData<List<Movie>> {
        return database.movieDao.getWatchedMovies()
    }

    fun getToWatchMovies(): LiveData<List<Movie>> {
        return database.movieDao.getToWatchedMovies()
    }

    suspend fun deleteMovies(id: Int) {
        withContext(Dispatchers.IO) {
            database.movieDao.deleteMovie(id)
        }
    }

    suspend fun getPopularMovies(page: Int, region: String): List<Movie> =
        withContext(Dispatchers.IO) {
            TMDbApi.retrofitService.getPopularMovies(page, region).results.toDatabase(database)
        }

    suspend fun getUpcomingMovies(page: Int, region: String): List<Movie> =
        withContext(Dispatchers.IO) {
            TMDbApi.retrofitService.getUpcomingMovies(page, region).results.toDatabase(database)
        }

    suspend fun getTopRatedMovies(page: Int, region: String): List<Movie> =
        withContext(Dispatchers.IO) {
            TMDbApi.retrofitService.getTopRatedMovies(page, region).results.toDatabase(database)
        }

    suspend fun getNowPlayingMovies(page: Int, region: String): List<Movie> =
        withContext(Dispatchers.IO) {
            TMDbApi.retrofitService.getNowPlayingMovies(page, region).results.toDatabase(database)
        }

    suspend fun getSearchMovie(page: Int, query: String, region: String): List<Movie> =
        withContext(Dispatchers.IO) {
            TMDbApi.retrofitService.getSearchMovie(page, query, region).results.toDatabase(database)
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