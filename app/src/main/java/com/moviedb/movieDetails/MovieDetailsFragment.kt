package com.moviedb.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.tabs.TabLayoutMediator
import com.moviedb.databinding.MovieDetailsFragmentBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movie_details_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class MovieDetailsFragment : Fragment() {

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var viewModelFactory: MovieDetailsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = requireNotNull(this.activity)
        viewModelFactory =
            MovieDetailsViewModelFactory(requireArguments().getInt("movieId"), activity.application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MovieDetailsViewModel::class.java)

        val binding = MovieDetailsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        NavigationUI.setupWithNavController(
            binding.toolbar,
            NavHostFragment.findNavController(requireParentFragment().myNavHostFragment)
        )

        viewModel.details.observe(viewLifecycleOwner, Observer {
            binding.genreList.text = it.genres.joinToString { genre ->
                genre.name
            }
            val parser = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val formatter = SimpleDateFormat("MMM yyyy", Locale.US)
            val output = formatter.format(parser.parse(it.release_date)!!)
            binding.monthYearRelease.text = output
            binding.voteScore.text = it.vote_average.toString()
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = viewpager
        viewPager.adapter = MovieDetailsAdapter(this, viewModel)

        TabLayoutMediator(tabs, viewpager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Overview"
                    }
                    1 -> {
                        tab.text = "Credits"
                    }
                    2 -> {
                        tab.text = "Recommendations"
                    }
                }
            }).attach()
    }
}
