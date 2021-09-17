package com.task.tsjtask.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    fun <Api> buildApi(
        api: Class<Api>,
        accessToken: String? = null
    ): Api {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(OkHttpClient.Builder()
                .also { client ->
                    val logging =
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }


}