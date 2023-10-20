package com.example.movie.ui.single_movie_detail



import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.data.api.POSTER_BASE_URL
import com.example.movie.data.api.TheMovieDBClient
import com.example.movie.data.api.TheMovieDBInterface

import com.example.movie.data.vo.MovieDetails


import java.text.NumberFormat
import java.util.*


class SingleMovie : AppCompatActivity(){


    private lateinit var movie_title: TextView
    private lateinit var movie_tagline: TextView
    private lateinit var movie_release_date: TextView
    private lateinit var movie_rating: TextView
    private lateinit var movie_runtime: TextView
    private lateinit var movie_budget: TextView
    private lateinit var movie_revenue: TextView
    private lateinit var movie_overview: TextView
    private lateinit var iv_movie_poster: ImageView
    private lateinit var txt_error: TextView
    private lateinit var progress_bar_popular: ProgressBar

    private lateinit var viewModel: SingleMovieViewModel
    lateinit var movieRepository: MovieDetailsRepository



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        progress_bar_popular = findViewById(R.id.progress_bar_popular)
        txt_error = findViewById(R.id.txt_error)
        iv_movie_poster = findViewById(R.id.iv_movie_poster)

        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)


        val movieId: Int = intent.getIntExtra("id",1)
        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility = if (it == NextWorkState.LOADING) View.GONE else View.VISIBLE
            txt_error.visibility = if (it.status == Status.FAILED) View.VISIBLE else View.GONE

        })


    }
    fun bindUI(it: MovieDetails) {
        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)

        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.rating
        movie_runtime.text = it.runtime.toString() + " minutes"
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)
        movie_overview.text = it.overview

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster)
    }




    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SingleMovieViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return SingleMovieViewModel(movieRepository, movieId) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        })[SingleMovieViewModel::class.java]
    }

}
