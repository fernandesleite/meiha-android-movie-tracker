package com.moviedb.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.moviedb.R
import kotlinx.android.synthetic.main.fragment_movie_details.*

class MovieList : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = viewpager
        viewPager.adapter = MovieListPagerAdapter(this)

        TabLayoutMediator(tabs, viewpager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Popular"
                    }
                    1 -> {
                        tab.text = "Now Playing"
                    }
                    2 -> {
                        tab.text = "Upcoming"
                    }
                    3 -> {
                        tab.text = "Top Rated"
                    }
                }
            }).attach()
    }


}

