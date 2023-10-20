package com.example.movie.ui.single_movie_detail

import NextWorkState
import androidx.lifecycle.LiveData
import com.example.movie.data.api.TheMovieDBInterface
import com.example.movie.data.repository.MovieDetailsNetworkDataSource
//import com.example.movie.data.repository.NextWorkState
import com.example.movie.data.vo.MovieDetails
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MovieDetailsRepository  (private val apiService : TheMovieDBInterface) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails (
        compositeDisposable: CompositeDisposable,
        movieId: Int) : LiveData<MovieDetails> {

        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse

    }

    fun getMovieDetailsNetworkState(): LiveData<NextWorkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}
