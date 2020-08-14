package com.moviedb.movieList

import androidx.lifecycle.LiveData
import com.moviedb.persistence.Movie

class PopularMovieListFragment : MovieListBaseFragment() {
    override fun getMovieList(): LiveData<List<Movie>> {
        return viewModel.popularMovies
    }
}