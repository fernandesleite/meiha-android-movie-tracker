package com.moviedb.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.moviedb.R
import com.moviedb.movieDetails.MovieCreditsAdapter
import com.moviedb.movieList.MovieListAdapter
import com.moviedb.network.TMDbMovieCredits
import com.moviedb.persistence.Movie
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl.let {
        if (it == null) {
            Glide.with(imgView.context).load(R.drawable.ic_broken_image).into(imgView)
        } else {
            val fullUri = "https://image.tmdb.org/t/p/original$imgUrl"
            val imgUri = fullUri.toUri().buildUpon().scheme("https").build()
            Glide.with(imgView.context)
                .load(imgUri)
                .placeholder(R.drawable.ic_broken_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(RoundedCorners(8))
                .into(imgView)
        }
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: List<Movie>?
) {
    val adapter = recyclerView.adapter as MovieListAdapter
    adapter.submitList(data)
}

@BindingAdapter("listCredits")
fun bindRecyclerViewCredits(
    recyclerView: RecyclerView,
    data: List<TMDbMovieCredits.Cast>?
) {
    val adapter = recyclerView.adapter as MovieCreditsAdapter
    adapter.submitList(data)
}

@BindingAdapter("yearReleased")
fun bindMovieDateYear(textView: TextView, date: String?) {
    try {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val formatter = SimpleDateFormat("yyyy", Locale.US)
        val output = formatter.format(parser.parse(date)!!)
        textView.text = output.toString()
    } catch (e: Exception) {
        textView.text = "-"
    }
}

@BindingAdapter("intToCurrency")
fun bindIntToCurrency(textView: TextView, int: Long) {
    val currency = NumberFormat.getInstance(Locale.US).format(int)
    textView.text = if (int == 0.toLong()) "-" else "$$currency.00"
}
