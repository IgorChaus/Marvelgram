package com.example.marvelvm.source

import android.util.Log
import com.example.marvelvm.api.ApiInterface
import com.example.marvelvm.model.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepository @Inject constructor(private val service: ApiInterface) {
    suspend fun getPersons(): Flow<List<Person>> = flow  {
        var persons:List<Person> = listOf()
        withContext(Dispatchers.IO) {
            try {
                val response = service.getPersons()
                if (response.isSuccessful) {
                    persons = response.body()!!
                }else {
                    persons = listOf()
                    Log.i("MyTag", "Error reading data")
                }
            } catch (e: Exception) {
                Log.i("MyTag", "Error reading data")
            }
        }

        emit(persons)
    }
}


