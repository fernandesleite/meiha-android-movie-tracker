package com.moviedb.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.moviedb.databinding.FragmentMovieCreditsBinding

class MovieCreditsFragment(val viewModel: MovieDetailsViewModel) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        val binding = FragmentMovieCreditsBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.creditsList.adapter = MovieCreditsAdapter()
        return binding.root
    }
}
