package com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.data.Resource
import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.repository.MovieRepository
import com.inspirecoding.omdb_mvvm_kotlinflow_daggerhilt_retrofit.utils.API_KEY
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.Movie
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel @ViewModelInject constructor (
    private val movieRepository: MovieRepository
): ViewModel() {

    val TAG = this.javaClass.simpleName

    private val _movieResult = MutableLiveData<Resource<Movie>>()
    val movieResult : LiveData<Resource<Movie>> = _movieResult

    fun getMovieDetailsData(imdbId : String) {

        viewModelScope.launch {

            movieRepository.getMovieDetailsData(imdbId, API_KEY)
                .onStart {
                    _movieResult.postValue(Resource.Loading(true))
                }
                .catch {
                    it.message?.let { message ->
                        _movieResult.postValue(Resource.Error(message))
                    }
                }
                .collect {  movie ->
                    _movieResult.postValue(Resource.Success(movie))
                }


        }

    }


}