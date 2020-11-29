package com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.repository

import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.repository.remote.MovieEndpoints
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.Movie
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.SearchResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieEndpoints: MovieEndpoints
) : MovieRepository {

    override fun getSearchResultData(
        searchTitle: String,
        apiKey: String,
        pageIndex: Int
    ): Flow<SearchResults> {

        return flow {
            val searchResult = movieEndpoints.getSearchResultData(
                searchTitle, apiKey, pageIndex
            )

            emit(searchResult)
        }.flowOn(Dispatchers.IO)


    }

    override fun getMovieDetailsData(imdbId: String, apiKey: String): Flow<Movie> {
        return flow {
            val movie = movieEndpoints.getMovieDetailsData(
                imdbId, apiKey
            )
            emit(movie)
        }.flowOn(Dispatchers.IO)
    }
}