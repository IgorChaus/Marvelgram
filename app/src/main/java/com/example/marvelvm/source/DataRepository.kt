package com.example.kode_viewmodel.source

import android.util.Log
import com.example.marvelvm.api.ApiInterface
import com.example.marvelvm.model.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataRepository(val service: ApiInterface) {
    suspend fun getPersons(): List<Person> {
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

        return persons
    }
}

