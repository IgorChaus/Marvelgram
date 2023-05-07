package com.example.marvelvm.source

import com.example.marvelvm.api.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://static.upstarts.work/tests/marvelgram/"

    private val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service = builder.create(ApiInterface::class.java)
}