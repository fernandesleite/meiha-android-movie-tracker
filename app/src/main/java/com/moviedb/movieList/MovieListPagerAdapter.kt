package com.moviedb.movieList

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class MovieListPagerAdapter(private val fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PopularMovieListFragment()
            1 -> UpcomingMovieListFragment()
            else -> PopularMovieListFragment()
        }
    }
}