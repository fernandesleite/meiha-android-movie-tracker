package com.moviedb.movieList

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.moviedb.R
import com.moviedb.databinding.MovieListFragmentBinding
import com.moviedb.movieDetails.MovieDetailsAdapter
import kotlinx.android.synthetic.main.movie_details_fragment.*

class MovieList : Fragment() {

    private lateinit var viewModel: MovieListViewModel
    private var adapter = MovieListAdapter()
    private lateinit var binding: MovieListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_list_fragment, container, false)
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
                        tab.text = "Upcoming"
                    }
                }
            }).attach()
    }


}

