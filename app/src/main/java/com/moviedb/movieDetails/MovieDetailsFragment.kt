package com.moviedb.movieDetails

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.moviedb.R
import com.moviedb.databinding.FragmentMovieDetailsBinding
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_movie_details.*
import java.text.ParseException
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
        val movieId = requireArguments().getInt("movieId")
        viewModelFactory =
            MovieDetailsViewModelFactory(movieId, activity.application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MovieDetailsViewModel::class.java)

        val binding = FragmentMovieDetailsBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val toolbar = binding.toolbar
        NavigationUI.setupWithNavController(
            toolbar,
            NavHostFragment.findNavController(requireParentFragment())
        )

        viewModel.getMovie(movieId).observe(viewLifecycleOwner, Observer { int ->
            toolbar.menu.clear()
            when (int) {
                1 -> {
                    toolbar.inflateMenu(R.menu.watched_group_menu)
                }
                2 -> {
                    toolbar.inflateMenu(R.menu.to_watch_group_menu)
                }
                else -> {
                    toolbar.inflateMenu(R.menu.unlabeled_group_menu)
                }
            }
        })

        toolbar.setOnMenuItemClickListener { item ->
            viewModel.apply {
                details.observe(viewLifecycleOwner, Observer {
                    when (item.itemId) {
                        R.id.add_watched -> {
                            movieToCache(1, it.id)
                            Toast.makeText(context, "Movie watched", Toast.LENGTH_SHORT).show()
                        }
                        R.id.add_to_watch -> {
                            Toast.makeText(context, "Movie to watch", Toast.LENGTH_SHORT).show()
                            movieToCache(2, it.id)
                        }
                        R.id.delete -> {
                            Toast.makeText(context, "Movie deleted from lists", Toast.LENGTH_SHORT)
                                .show()
                            deleteMovie(it.id)
                        }
                    }
                })
            }
            true
        }

        viewModel.details.observe(viewLifecycleOwner, Observer {

            binding.apply {
                genreList.text = it.genres.joinToString { genre ->
                    genre.name
                }
                val parser = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                val formatter = SimpleDateFormat("MMM yyyy", Locale.US)
                try {
                    val output = formatter.format(parser.parse(it.release_date)!!)
                    monthYearRelease.text = output
                    voteScore.text = it.vote_average.toString()
                } catch (e: ParseException) {
                    voteScore.text = "-"
                }

                val fullUri = "https://image.tmdb.org/t/p/original${it.backdrop_path}"
                val imgUri = fullUri.toUri().buildUpon().scheme("https").build()
                context?.let { context ->
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
                                progressBar.visibility = View.GONE
                                detailsLayout.visibility = View.VISIBLE
                                return false
                            }

                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: com.bumptech.glide.request.target.Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progressBar.visibility = View.GONE
                                detailsLayout.visibility = View.VISIBLE
                                return false
                            }
                        })
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transform(RoundedCorners(8))
                        .apply(RequestOptions.bitmapTransform(BlurTransformation(15, 1)))
                        .into(backdropImage)
                }
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

    override fun onResume() {
        super.onResume()
        val activity = requireNotNull(this.activity)
        activity.findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.GONE
    }
}
