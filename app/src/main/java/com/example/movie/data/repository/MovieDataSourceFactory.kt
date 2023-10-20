package com.example.movie.data.repository

import android.graphics.Movie
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.movie.data.api.TheMovieDBInterface
import io.reactivex.rxjava3.disposables.CompositeDisposable


class MovieDataSourceFactory(
    private val apiService: TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> { // Correct override keyword
        val movieDataSource = MovieDataSource(apiService, compositeDisposable)
        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }

    fun getItemLiveDataSource(): MutableLiveData<MovieDataSource> {
        return moviesLiveDataSource
    }
}
