package com.moviedb.movieDetails

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class MovieDetailsPagerAdapter(fragment: Fragment, val viewModel: MovieDetailsViewModel) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MovieOverviewFragment(viewModel)
            1 -> MovieCreditsFragment(viewModel)
            2 -> MovieRecommendationsFragment(viewModel)
            else -> MovieOverviewFragment(viewModel)
        }
    }
}