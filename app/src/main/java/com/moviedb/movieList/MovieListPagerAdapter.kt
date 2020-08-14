package com.moviedb.movieList

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class MovieListPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PopularMovieListFragment()
            1 -> NowPlayingMovieListFragment()
            2 -> UpcomingMovieListFragment()
            3 -> TopRatedMovieListFragment()
            else -> PopularMovieListFragment()
        }
    }
}