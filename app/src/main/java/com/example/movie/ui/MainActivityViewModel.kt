package com.example.movie.ui

import androidx.lifecycle.ViewModel
import com.example.movie.ui.MoviePagedListRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class PopularViewModel (
    private val movieRepository : MoviePagedListRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  moviePagedList by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val  networkState by lazy {
        movieRepository.getNetworkState()
    }


    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}