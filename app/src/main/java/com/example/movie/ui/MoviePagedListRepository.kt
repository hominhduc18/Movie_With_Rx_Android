package com.example.movie.ui


import NextWorkState
import android.graphics.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movie.data.api.POST_PER_PAGE
import com.example.movie.data.api.TheMovieDBInterface
import com.example.movie.data.repository.MovieDataSource
import com.example.movie.data.repository.MovieDataSourceFactory
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MoviePagedListRepository (
    private val apiService : TheMovieDBInterface) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory =
            MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList

    }



    fun getNetworkState(): LiveData<NextWorkState> {
        return Transformations.switchMap<MovieDataSource, NextWorkState>(
            moviesDataSourceFactory.getItemLiveDataSource()) { it.networkState }
    }


}