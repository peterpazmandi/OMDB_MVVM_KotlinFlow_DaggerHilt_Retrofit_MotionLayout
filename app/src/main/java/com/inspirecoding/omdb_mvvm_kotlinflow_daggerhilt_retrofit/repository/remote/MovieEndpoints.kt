package com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.repository.remote

import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.Movie
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.SearchResults
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieEndpoints {

    @GET("?type=movie")
    suspend fun getSearchResultData(
        @Query(value = "s") searchTitle : String,
        @Query(value = "apiKey") apiKey : String,
        @Query(value = "page") pageIndex : Int
    ) : SearchResults

    @GET("?plot=full")
    suspend fun getMovieDetailsData(
        @Query(value = "i") imdbId : String,
        @Query(value = "apiKey") apiKey: String
    ) : Movie
}