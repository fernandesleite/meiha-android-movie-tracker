package com.moviedb.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.moviedb.databinding.MovieListFragmentBinding

class MovieList : Fragment() {

    private lateinit var viewModel: MovieListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
        val binding = MovieListFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.movieList.adapter = MovieListAdapter()

        viewModel.popularMovies.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.GONE
        })
        return binding.root
    }

}
