package com.moviedb.movieDetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MovieDetailsViewModelFactory(
    private val movieId: Int,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(movieId, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}