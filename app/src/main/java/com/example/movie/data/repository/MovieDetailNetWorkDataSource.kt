package com.example.movie.data.repository

import NextWorkState
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movie.data.api.TheMovieDBInterface
import com.example.movie.data.vo.MovieDetails
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieDetailsNetworkDataSource (
    private val apiService : TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState  = MutableLiveData<NextWorkState>()
    val networkState: LiveData<NextWorkState>
        get() = _networkState

    private val _downloadedMovieDetailsResponse =  MutableLiveData<MovieDetails>()
    val downloadedMovieResponse: LiveData<MovieDetails>
        get() = _downloadedMovieDetailsResponse

    fun fetchMovieDetails(movieId: Int) {

        _networkState.postValue(NextWorkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())   //
                    .subscribe(
                        {
                            _downloadedMovieDetailsResponse.postValue(it)
                            _networkState.postValue(NextWorkState.LOADED)
                        },
                        {
                            _networkState.postValue(NextWorkState(Status.FAILED, "Something went wrong"))
                            Log.e("MovieDetailsDataSource", it.message!!)
                        }
                    )
            )

        }

        catch (e: Exception){
            Log.e("MovieDetailsDataSource", e.message!!)
        }


    }

}
