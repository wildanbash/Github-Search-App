package com.bash.consumerapp.network.api

import com.bash.consumerapp.network.setting.BasicInterceptor
import com.bash.consumerapp.util.ACCESS_TOKEN
import com.bash.consumerapp.util.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        private val logger =HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        private val okHttp = OkHttpClient.Builder()
            .addInterceptor(BasicInterceptor(ACCESS_TOKEN))
            .addInterceptor(logger)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttp)
                .build()
        }

        val service by lazy {
            val create: ApiService = retrofit.create(
                ApiService::class.java)
            create
        }
    }
}