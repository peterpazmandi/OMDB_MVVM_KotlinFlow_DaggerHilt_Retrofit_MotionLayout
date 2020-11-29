package com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.repository

import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.repository.remote.MovieEndpoints
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.Movie
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.SearchResults
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MovieRepository {

    fun getSearchResultData(
        searchTitle : String, apiKey : String, pageIndex : Int
    ) : Flow<SearchResults>

    fun getMovieDetailsData(
        imdbId : String, apiKey: String
    ) : Flow<Movie>

}