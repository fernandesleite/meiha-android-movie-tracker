package com.moviedb.movieDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.moviedb.movieList.MovieRepository
import com.moviedb.persistence.MoviesAppDatabase

class MovieDetailsViewModel(movieId: Int, application: Application) :
    AndroidViewModel(application) {
    private val movieRepository = MovieRepository(MoviesAppDatabase.getInstance(application))
    var movie = movieRepository.getMovieDetails(movieId)
}
