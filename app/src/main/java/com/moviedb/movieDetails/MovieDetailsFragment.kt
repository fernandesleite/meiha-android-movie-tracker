package com.moviedb.movieDetails

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.moviedb.databinding.FragmentMovieDetailsBinding
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_movie_details.*
import java.text.SimpleDateFormat
import java.util.*

class MovieDetailsFragment : Fragment() {

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var viewModelFactory: MovieDetailsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = requireNotNull(this.activity)
        viewModelFactory =
            MovieDetailsViewModelFactory(requireArguments().getInt("movieId"), activity.application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MovieDetailsViewModel::class.java)

        val binding = FragmentMovieDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        NavigationUI.setupWithNavController(
            binding.toolbar,
            NavHostFragment.findNavController(requireParentFragment().myNavHostFragment)
        )

        viewModel.details.observe(viewLifecycleOwner, Observer {
            binding.genreList.text = it.genres.joinToString { genre ->
                genre.name
            }
            val parser = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val formatter = SimpleDateFormat("MMM yyyy", Locale.US)
            val output = formatter.format(parser.parse(it.release_date)!!)
            binding.monthYearRelease.text = output
            binding.voteScore.text = it.vote_average.toString()

            val fullUri = "https://image.tmdb.org/t/p/original${it.backdrop_path}"
            val imgUri = fullUri.toUri().buildUpon().scheme("https").build()
            context?.let { context ->
                Log.i("test", imgUri.toString())
                Glide.with(context)
                    .load(imgUri)
                    .listener(object : RequestListener<Drawable> {
                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.progressBar.visibility = View.GONE
                            binding.detailsLayout.visibility = View.VISIBLE
                            return false
                        }

                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.progressBar.visibility = View.GONE
                            binding.detailsLayout.visibility = View.VISIBLE
                            return false
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(RoundedCorners(8))
                    .apply(RequestOptions.bitmapTransform(BlurTransformation(15, 1)))
                    .into(binding.backdropImage)
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = viewpager
        viewPager.adapter = MovieDetailsPagerAdapter(this, viewModel)

        TabLayoutMediator(tabs, viewpager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Overview"
                    }
                    1 -> {
                        tab.text = "Credits"
                    }
                    2 -> {
                        tab.text = "More Like This"
                    }
                }
            }).attach()
    }
}
