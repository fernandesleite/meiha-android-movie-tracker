package com.moviedb.movieList

import androidx.lifecycle.LiveData
import com.moviedb.persistence.Movie

class TopRatedMovieListFragment : MovieListBaseFragment() {
    override fun getMovieList(): LiveData<List<Movie>> {
        return viewModel.topRatedMovies
    }
}