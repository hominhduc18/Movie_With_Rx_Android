package com.example.movie.data.repository

import NextWorkState
import android.graphics.Movie
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.movie.data.api.FIRST_PAGE
import com.example.movie.data.api.TheMovieDBInterface
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieDataSource (
    private val apiService : TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Movie>() {

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NextWorkState> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {

        networkState.postValue(NextWorkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.movieList, null, page+1)
                        networkState.postValue(NextWorkState.LOADED)
                    },
                    {
                        networkState.postValue(NextWorkState(Status.FAILED, "Something went wrong"))
                        Log.e("MovieDataSource", it.message!!)
                    }
                )
        )

    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Movie>) {

        networkState.postValue(NextWorkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMovie(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.totalPages >= params.key){
                            callback.onResult(it.movieList, params.key+1)
                            networkState.postValue(NextWorkState.LOADED)
                        }
                        else{
                            networkState.postValue(NextWorkState(Status.FAILED, "You have reached the end"))
                        }

                    },
                    {
                        networkState.postValue(NextWorkState(Status.FAILED, "Something went wrong"))
                        Log.e("MovieDataSource", it.message!!)
                    }
                )
        )

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }
}
