package com.moviedb.movieList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviedb.network.TMDbApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieListViewModel : ViewModel() {
    private val _response = MutableLiveData<String>()
    val response : LiveData<String>
        get() = _response

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getTMDbApiServiceMovies()
    }

    private fun getTMDbApiServiceMovies() {
        coroutineScope.launch {
            val getMovieListSuspended = TMDbApi.retrofitService.getPopularMovies()
            try {
                val listResult = getMovieListSuspended
                _response.value = listResult.toString()
            }   catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }
}


