package com.example.marvelvm.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.kode_viewmodel.source.DataRepository
import com.example.marvelvm.model.Person
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class AppViewModel(private val dataRepository: DataRepository): ViewModel() {

    // Object must be observable but with a private setter, so we separate LiveData's objects
    private val itemsDataEmitter: MutableLiveData<List<Person>> = MutableLiveData()
    val itemsLiveData: LiveData<List<Person>> = itemsDataEmitter

    private var strSearch: String = ""

    init{
        fetchPersons()
    }

    class Factory(private val dataRepository: DataRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AppViewModel(dataRepository) as T
        }
    }

    fun fetchPersons(){
        viewModelScope.launch {
            val persons = dataRepository.getPersons()
            itemsDataEmitter.postValue(persons)

        }
    }

}


