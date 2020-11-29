package com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.search

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.data.Resource
import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.repository.MovieRepository
import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.utils.API_KEY
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.Search
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SearchViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val TAG = this.javaClass.simpleName

    private var moviesList : ArrayList<Search> = ArrayList()
    private val _searchResult = MutableLiveData<Resource<ArrayList<Search>>>()
    val searchResult : LiveData<Resource<ArrayList<Search>>> = _searchResult


    fun getSearchResultData(searchTitle: String) {

        viewModelScope.launch {

            moviesList.clear()

            movieRepository
                .getSearchResultData(searchTitle, API_KEY, 1)
                .onStart {
                    _searchResult.postValue(Resource.Loading(true))
                }
                .catch { exception ->
                    exception.message?.let {
                        _searchResult.postValue(Resource.Error(it))
                    }
                }
                .collect { _moviesList ->
                    _moviesList.Search?.let { listOfMovies ->
                        moviesList.addAll(listOfMovies)

                        moviesList.sortByDescending {
                            it.Year
                        }

                        _searchResult.postValue(Resource.Success(moviesList))
                    }
                }

        }

    }

}