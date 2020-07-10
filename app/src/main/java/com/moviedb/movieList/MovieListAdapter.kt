package com.moviedb.movieList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moviedb.R
import com.moviedb.databinding.ListItemMovieBinding
import com.moviedb.persistence.Movie
import kotlinx.android.synthetic.main.list_item_movie.view.*

class MovieListAdapter : ListAdapter<Movie, MovieListAdapter.MovieViewHolder>(DiffCallback) {
    class MovieViewHolder(private val binding: ListItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()
        }
    }

    val list = mutableListOf<Movie>()
    fun addItems(items: MutableList<Movie>) {
        if (!currentList.containsAll(items)) {
            list.addAll(items)
            submitList(list)
            notifyDataSetChanged()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ListItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)

        holder.bind(movie)
        holder.itemView.genre_list.text = movie.genre_ids?.joinToString()
        holder.itemView.setOnClickListener { view ->
            val bundle = bundleOf("movieId" to movie.id)
            view.findNavController().navigate(R.id.movieDetailsFragment, bundle)
        }
    }
}