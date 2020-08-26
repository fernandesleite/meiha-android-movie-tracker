package com.moviedb.movieList

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import com.moviedb.persistence.Movie

class NowPlayingMovieListFragment : MovieListBaseFragment() {
    override fun getMovieList(): LiveData<List<Movie>> {
        return viewModel.nowPlayingMovies
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addPagination()
    }
}