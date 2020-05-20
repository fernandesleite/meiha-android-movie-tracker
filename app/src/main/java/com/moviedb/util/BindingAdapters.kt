package com.moviedb.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.moviedb.movieDetails.MovieCreditsAdapter
import com.moviedb.movieList.MovieListAdapter
import com.moviedb.network.TMDbMovieCredits
import com.moviedb.persistence.Movie
import jp.wasabeef.glide.transformations.BlurTransformation
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val fullUri = "https://image.tmdb.org/t/p/original$imgUrl"
        val imgUri = fullUri.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(RoundedCorners(8))
            .into(imgView)
    }
}

@BindingAdapter("imageBlur")
fun bindImageBlur(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val fullUri = "https://image.tmdb.org/t/p/original$imgUrl"
        val imgUri = fullUri.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(RoundedCorners(8))
            .apply(RequestOptions.bitmapTransform(BlurTransformation(15, 1)))
            .into(imgView)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<Movie>?) {
    val adapter = recyclerView.adapter as MovieListAdapter
    adapter.submitList(data)
}

@BindingAdapter("listCredits")
fun bindRecyclerViewCredits(recyclerView: RecyclerView,
                     data: List<TMDbMovieCredits.Cast>?) {
    val adapter = recyclerView.adapter as MovieCreditsAdapter
    adapter.submitList(data)
}

@BindingAdapter("yearReleased")
fun bindMovieDateYear(textView: TextView, date: String) {
    val parser = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val formatter = SimpleDateFormat("yyyy", Locale.US)
    val output = formatter.format(parser.parse(date)!!)
    textView.text = output.toString()
}

@BindingAdapter("intToCurrency")
fun bindIntToCurrency(textView: TextView, int: Long) {
    val currency = NumberFormat.getInstance(Locale.US).format(int)
    textView.text = if(int == 0.toLong()) "-" else "$$currency.00"
}
