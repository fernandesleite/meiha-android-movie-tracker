package com.moviedb.movieList

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.moviedb.R
import com.moviedb.databinding.FragmentSearchMovieListBinding
import com.moviedb.util.KeyboardBehaviour
import kotlinx.android.synthetic.main.activity_main.*


class SearchMovieListFragment : Fragment() {
    lateinit var viewModel: MovieListViewModel
    lateinit var binding: FragmentSearchMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        binding = FragmentSearchMovieListBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)


        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.movieList.adapter = MovieListAdapter()
        val mAdapter: MovieListAdapter = binding.movieList.adapter as MovieListAdapter

        NavigationUI.setupWithNavController(
            binding.toolbar,
            NavHostFragment.findNavController(requireParentFragment().myNavHostFragment)
        )

        viewModel.searchMovies.observe(viewLifecycleOwner, Observer {
            mAdapter.addItems(it.toMutableList())
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.search_bar)

        val searchItem = toolbar.menu.findItem(R.id.search_bar)
        val sv = searchItem.actionView as SearchView
        sv.setIconifiedByDefault(false)
        sv.requestFocus()
        KeyboardBehaviour.showKeyboard(context as Activity)
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getSearchQuery(query ?: "")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getSearchQuery(newText ?: "")
                val mAdapter: MovieListAdapter = binding.movieList.adapter as MovieListAdapter
                mAdapter.removeItems()
                return false
            }

        })

    }
}