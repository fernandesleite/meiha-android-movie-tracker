package com.moviedb.movieList

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.moviedb.persistence.Movie
import com.moviedb.persistence.MoviesAppDatabase
import com.moviedb.repositories.GenreRepository
import com.moviedb.repositories.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class MovieListViewModel(application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val movieRepository = MovieRepository(
        MoviesAppDatabase.getInstance(application.applicationContext)
    )
    private val genreRepository = GenreRepository(
        MoviesAppDatabase.getInstance(application.applicationContext)
    )
    val genres = genreRepository.genres
    private val _page = MutableLiveData<Int>()
    val page: LiveData<Int>
        get() = _page

    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>>
        get() = _popularMovies

    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingMovies: LiveData<List<Movie>>
        get() = _upcomingMovies

    private val _topRatedMovies = MutableLiveData<List<Movie>>()
    val topRatedMovies: LiveData<List<Movie>>
        get() = _topRatedMovies

    private val _nowPlayingMovies = MutableLiveData<List<Movie>>()
    val nowPlayingMovies: LiveData<List<Movie>>
        get() = _nowPlayingMovies

    private val _searchMovies = MutableLiveData<List<Movie>>()
    val searchMovies: LiveData<List<Movie>>
        get() = _searchMovies

    private var _searchQuery = ""
    val searchQuery: String
        get() = _searchQuery

    val watched: LiveData<List<Movie>>
        get() = movieRepository.getWatchedMovies()

    val toWatch: LiveData<List<Movie>>
        get() = movieRepository.getToWatchMovies()

    private val country = Locale.getDefault().country
    private val language = Locale.getDefault().language

    init {
        _page.value = 1
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        coroutineScope.launch {
            try {
                genreRepository.refreshGenresOfflineCache(language)
                movieRepository.refreshMoviesOfflineCache()
                _popularMovies.value = _page.value?.let {
                    movieRepository.getPopularMovies(
                        it,
                        country,
                        language
                    )
                }
                _upcomingMovies.value = _page.value?.let {
                    movieRepository.getUpcomingMovies(
                        it,
                        country,
                        language
                    )
                }
                _topRatedMovies.value = _page.value?.let {
                    movieRepository.getTopRatedMovies(
                        it,
                        country,
                        language
                    )
                }
                _nowPlayingMovies.value =
                    _page.value?.let {
                        movieRepository.getNowPlayingMovies(
                            it,
                            country,
                            language
                        )
                    }
            } catch (e: Exception) {
                Log.e("MovieListViewModel", e.message, e)
            }
        }
    }

    fun resetPage() {
        _page.value = 1
    }

    fun nextPage() {
        _page.value = _page.value?.plus(1)
        refreshDataFromRepository()
    }

    fun getMovie(movieId: Int): LiveData<Int> {
        return movieRepository.getMovie(movieId)
    }

    fun getSearchQuery(query: String) {
        _searchQuery = query
        coroutineScope.launch {
            try {
                _searchMovies.value =
                    _page.value?.let {
                        movieRepository.getSearchMovie(
                            it,
                            _searchQuery,
                            country,
                            language
                        )
                    }

            } catch (e: Exception) {
                Log.e("MovieListViewModel", e.message, e)
            }
        }
    }
}


