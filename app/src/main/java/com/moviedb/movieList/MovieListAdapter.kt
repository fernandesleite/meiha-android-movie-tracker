package com.moviedb.movieList

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moviedb.R
import com.moviedb.databinding.ItemListMovieBinding
import com.moviedb.persistence.Movie
import com.moviedb.util.KeyboardBehaviour

class MovieListAdapter : ListAdapter<Movie, MovieListAdapter.MovieViewHolder>(DiffCallback) {
    class MovieViewHolder(private val binding: ItemListMovieBinding) :
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

    fun removeItems() {
        list.clear()
        submitList(list)
        notifyDataSetChanged()
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
            ItemListMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.itemView.findViewById<TextView>(R.id.genre_list).text =
            movie.genre_ids?.joinToString()
        when (movie.category) {
            1 -> holder.itemView.findViewById<ImageView>(R.id.watch_image).visibility = View.VISIBLE
            2 -> {
                holder.itemView.findViewById<ImageView>(R.id.watch_image).visibility = View.VISIBLE
                holder.itemView.findViewById<ImageView>(R.id.watch_image)
                    .setImageResource(R.drawable.ic_pending_18dp)
            }
            else -> {
                holder.itemView.findViewById<ImageView>(R.id.watch_image).visibility = View.GONE
            }

        }

        holder.itemView.setOnClickListener { view ->
            val bundle = bundleOf("movieId" to movie.id)

            view.findNavController().navigate(R.id.movieDetailsFragment, bundle)
            KeyboardBehaviour.hideKeyboard(view.context as Activity)
        }
        holder.bind(movie)

    }
}