package com.moviedb.movieDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moviedb.databinding.ListItemCreditsBinding
import com.moviedb.movieList.MovieListAdapter
import com.moviedb.network.TMDbMovieCredits

class MovieCreditsAdapter: ListAdapter<TMDbMovieCredits.Cast, MovieCreditsAdapter.CreditsViewHolder>(DiffCallback) {
    class CreditsViewHolder(private val binding: ListItemCreditsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(credits: TMDbMovieCredits.Cast) {
            binding.credits = credits
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<TMDbMovieCredits.Cast>() {
        override fun areItemsTheSame(
            oldItem: TMDbMovieCredits.Cast,
            newItem: TMDbMovieCredits.Cast
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: TMDbMovieCredits.Cast,
            newItem: TMDbMovieCredits.Cast
        ): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditsViewHolder {
        return CreditsViewHolder(
            ListItemCreditsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CreditsViewHolder, position: Int) {
        val credit = getItem(position)
        holder.bind(credit)
    }

}