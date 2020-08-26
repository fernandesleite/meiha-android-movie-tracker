package com.moviedb.movieList

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import com.moviedb.persistence.Movie

class WatchedMovieListFragment: MovieListBaseFragment() {
    override fun getMovieList(): LiveData<List<Movie>> {
        return viewModel.watched
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarLayout.appBar.visibility = View.VISIBLE
        mAdapter.removeItems()
    }

}
