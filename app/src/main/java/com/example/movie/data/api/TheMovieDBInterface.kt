package com.example.movie.data.api

import com.example.movie.data.vo.MovieDetails
import com.example.movie.data.vo.MovieResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface{

    fun getPopularMovie(
        @Query("page") page: Int
    ): Single<MovieResponse>


    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") id: Int
    ): Single<MovieDetails>
}
