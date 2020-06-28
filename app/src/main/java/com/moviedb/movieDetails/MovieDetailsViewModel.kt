package com.moviedb.movieDetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.moviedb.network.TMDbMovieCredits
import com.moviedb.network.TMDbMovieDetails
import com.moviedb.persistence.Movie
import com.moviedb.persistence.MoviesAppDatabase
import com.moviedb.repositories.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieDetailsViewModel(movieId: Int, application: Application) :
    AndroidViewModel(application) {
    private val movieRepository = MovieRepository(
        MoviesAppDatabase.getInstance(application)
    )

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _details = MutableLiveData<TMDbMovieDetails>()
    val details: LiveData<TMDbMovieDetails>
        get() = _details

    private val _credits = MutableLiveData<TMDbMovieCredits>()
    val credits: LiveData<TMDbMovieCredits>
        get() = _credits

    private val _recommendations = MutableLiveData<List<Movie>>()
    val recommendations: LiveData<List<Movie>>
        get() = _recommendations

    init {
        getMovieInfo(movieId)
    }

    private fun getMovieInfo(movieId: Int) {
        try {
            coroutineScope.launch {
                _details.value = movieRepository.getMovieDetails(movieId)
                _credits.value = movieRepository.getMovieCredits(movieId)
                _recommendations.value = movieRepository.getMovieRecommendations(movieId)
            }
        } catch (e: Exception) {
            Log.e("MovieDetailsViewModel", e.message, e)
        }
    }
}
