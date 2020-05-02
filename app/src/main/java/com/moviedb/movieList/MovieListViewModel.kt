package com.moviedb.movieList

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.moviedb.persistence.MoviesAppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieListViewModel(application: Application) : AndroidViewModel(application){

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val movieRepository = MovieRepository(MoviesAppDatabase.getInstance(application.applicationContext))
    val movies = movieRepository.movies

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        coroutineScope.launch {
            try {
                movieRepository.refreshMoviesOfflineCache()
            }   catch (e: Exception) {
                Log.e("MovieListViewModel", e.message, e)
            }
        }
    }
}


