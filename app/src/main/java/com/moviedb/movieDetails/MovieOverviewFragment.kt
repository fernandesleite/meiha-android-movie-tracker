package com.moviedb.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.moviedb.databinding.FragmentMovieOverviewBinding

class MovieOverviewFragment(var viewModel: MovieDetailsViewModel) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        val binding = FragmentMovieOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.details.observe(viewLifecycleOwner, Observer {
            binding.spokenLanguages.text = it.spoken_languages.joinToString("\n\n") { language ->
                language.name
            }
            binding.productionCompanies.text =
                it.production_companies.joinToString("\n\n") { companies ->
                    companies.name
                }
            binding.progressBar.visibility = View.GONE
            binding.overviewLayout.visibility = View.VISIBLE
        })
        return binding.root
    }
}
