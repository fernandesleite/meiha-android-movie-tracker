package com.moviedb.movieList

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.moviedb.R
import com.moviedb.util.KeyboardBehaviour
import kotlinx.android.synthetic.main.fragment_movie_list.*


class MovieList : Fragment() {

    lateinit var viewModel: MovieListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        KeyboardBehaviour.hideKeyboard(context as Activity)
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = viewpager
        viewPager.adapter = MovieListPagerAdapter(this)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.search_menu)

        toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.search) {
                findNavController().navigate(R.id.searchMovieListFragment)
                true
            } else false
        }

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

