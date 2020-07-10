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

    init {
        _page.value = 1
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        coroutineScope.launch {
            try {
                genreRepository.refreshGenresOfflineCache()
                movieRepository.refreshMoviesOfflineCache()
                _popularMovies.value = _page.value?.let { movieRepository.getPopularMovies(it) }
            } catch (e: Exception) {
                Log.e("MovieListViewModel", e.message, e)
            }
        }
    }
    fun nextPage() {
        _page.value = _page.value?.plus(1)
        refreshDataFromRepository()
    }
}


