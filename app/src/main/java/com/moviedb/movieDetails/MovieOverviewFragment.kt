package com.moviedb.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.moviedb.databinding.FragmentMovieOverviewBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class MovieOverviewFragment(var viewModel: MovieDetailsViewModel) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMovieOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.details.observe(viewLifecycleOwner, Observer {
            binding.spokenLanguages.text = it.spoken_languages.joinToString("\n\n") { language ->
                language.name
            }
        })
        viewModel.details.observe(viewLifecycleOwner, Observer {
            binding.productionCompanies.text =
                it.production_companies.joinToString("\n\n") { companies ->
                    companies.name
                }
        })
        return binding.root
    }

}
