package com.moviedb.movieDetails

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter

class MovieDetailsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> MovieOverviewFragment()
            1 -> MovieCreditsFragment()
            2 -> MovieRecommendationsFragment()
            else -> MovieOverviewFragment()
        }
    }
}