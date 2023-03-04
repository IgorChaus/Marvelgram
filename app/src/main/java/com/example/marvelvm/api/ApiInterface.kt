package com.example.marvelvm.api

import com.example.marvelvm.model.Person
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("klsZdDg50j2.json")
    suspend fun getPersons() : Response<List<Person>>
}