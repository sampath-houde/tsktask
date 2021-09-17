package com.task.tsjtask.main.data.repo

import com.task.tsjtask.main.api.MovieApi
import com.task.tsjtask.utils.BaseRepository

class MovieRepo(
    private val api: MovieApi
) : BaseRepository(){

    suspend fun getTrendingMovies() = safeApiCall {
        api.getTrendingMovies()
    }
}