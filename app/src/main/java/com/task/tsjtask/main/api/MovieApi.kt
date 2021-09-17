package com.task.tsjtask.main.api

import com.task.tsjtask.main.data.model.get.TrendingResponse
import retrofit2.http.GET

interface MovieApi {

    @GET("trending/all/day?api_key=b7cd3340a794e5a2f35e3abb820b497f")
    suspend fun getTrendingMovies() : TrendingResponse

}