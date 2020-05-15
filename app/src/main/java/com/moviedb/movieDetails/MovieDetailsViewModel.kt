package com.moviedb.movieDetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.moviedb.movieList.MovieRepository
import com.moviedb.network.TMDbMovieCredits
import com.moviedb.network.TMDbMovieDetails
import com.moviedb.network.TMDbMovieRecommendations
import com.moviedb.persistence.MoviesAppDatabase
import kotlinx.coroutines.*

class MovieDetailsViewModel(movieId: Int, application: Application) :
    AndroidViewModel(application) {
    private val movieRepository = MovieRepository(MoviesAppDatabase.getInstance(application))

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _details = MutableLiveData<TMDbMovieDetails>()
    val details: LiveData<TMDbMovieDetails>
        get() = _details

    private val _credits = MutableLiveData<TMDbMovieCredits>()
    val credits: LiveData<TMDbMovieCredits>
        get() = _credits

    private val _recommendations = MutableLiveData<TMDbMovieRecommendations>()
    val recommendations: LiveData<TMDbMovieRecommendations>
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
        }catch (e: Exception){
            Log.e("MovieDetailsViewModel", e.message, e)
        }
    }
}
