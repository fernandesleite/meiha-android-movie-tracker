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
import com.moviedb.databinding.MovieListFragmentBinding

class MovieList : Fragment() {

    private lateinit var viewModel: MovieListViewModel
    private var adapter = MovieListAdapter()
    private lateinit var binding: MovieListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        viewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
        binding = MovieListFragmentBinding.inflate(inflater)
        binding.apply {
            lifecycleOwner = this@MovieList
            viewModel = viewModel
            movieList.adapter = adapter
        }

        val toTheTopButton = binding.floatingActionButton
        fun animateFloatingButtonToVisible() {
            toTheTopButton.apply {
                alpha = 0f
                visibility = View.VISIBLE
                animate().alpha(1f).setDuration(400).setListener(null)
            }
        }

        fun animateFloatingButtonToGone() {
            toTheTopButton.animate().alpha(0f).setDuration(400)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        toTheTopButton.visibility = View.GONE
                    }
                })
        }

        toTheTopButton.setOnClickListener {
            binding.movieList.scrollToPosition(0)
            animateFloatingButtonToGone()
        }

        var isLoading = false
        val mAdapter: MovieListAdapter = binding.movieList.adapter as MovieListAdapter

        viewModel.popularMovies.observe(viewLifecycleOwner, Observer {
            mAdapter.addItems(it.toMutableList())
            binding.progressBar.visibility = View.GONE
            isLoading = true
        })

        fun loadMoreItems() {
            viewModel.nextPage()
            isLoading = false
        }

        fun calcPositionToLoadItems(recyclerView: RecyclerView): Boolean {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisible = layoutManager.findFirstVisibleItemPosition()
            return firstVisible + visibleItemCount >= totalItemCount
        }

        binding.movieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy < 0) {
                    animateFloatingButtonToGone()
                } else if (toTheTopButton.visibility == View.GONE && dy > 0) {
                    animateFloatingButtonToVisible()
                }
                if (calcPositionToLoadItems(recyclerView) && isLoading) {
                    loadMoreItems()
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        adapter = binding.movieList.adapter as MovieListAdapter
    }
}

