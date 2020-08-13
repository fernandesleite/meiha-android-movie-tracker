package com.moviedb.movieList

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviedb.R
import com.moviedb.databinding.FragmentPopularMovieListBinding
import com.moviedb.databinding.MovieListFragmentBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PopularMovieListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PopularMovieListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var viewModel: MovieListViewModel
    private var adapter = MovieListAdapter()
    private lateinit var binding: FragmentPopularMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        viewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
        binding = FragmentPopularMovieListBinding.inflate(inflater)
        binding.apply {
            lifecycleOwner = this@PopularMovieListFragment
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

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment PopularMovieListFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            PopularMovieListFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}