package com.task.tsjtask.main.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.tsjtask.main.data.model.get.TrendingResponse
import com.task.tsjtask.main.data.repo.MovieRepo
import com.task.tsjtask.utils.ApiResponseHandler
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepo): ViewModel() {

    private val _movie_list: MutableLiveData<ApiResponseHandler<TrendingResponse>> =
        MutableLiveData()

    val movieList: LiveData<ApiResponseHandler<TrendingResponse>>
        get() = _movie_list

    fun getTrendingMovies() = viewModelScope.launch {
        _movie_list.value = repository.getTrendingMovies()
    }
}