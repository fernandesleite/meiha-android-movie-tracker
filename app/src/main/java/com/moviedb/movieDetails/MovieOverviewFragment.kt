package com.moviedb.movieDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.moviedb.R
import com.moviedb.databinding.FragmentMovieOverviewBinding
import com.moviedb.databinding.MovieDetailsFragmentBinding

class MovieOverviewFragment() : Fragment() {

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var viewModelFactory: MovieDetailsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = requireNotNull(this.activity)
        viewModelFactory = MovieDetailsViewModelFactory(requireParentFragment().requireArguments().getInt("movieId"), activity.application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MovieDetailsViewModel::class.java)

        val binding = FragmentMovieOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

}
