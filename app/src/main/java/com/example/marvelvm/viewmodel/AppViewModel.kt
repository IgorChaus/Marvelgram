package com.example.marvelvm.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.marvelvm.model.Person
import com.example.marvelvm.source.DataRepository
import com.example.marvelvm.wrappers.DarkItem
import com.example.marvelvm.wrappers.AdapterItems
import com.example.marvelvm.wrappers.OrdinaryItem
import kotlinx.coroutines.launch
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
class AppViewModel @Inject constructor(private val dataRepository: DataRepository): ViewModel() {

    private val _itemList: MutableLiveData<List<AdapterItems>> = MutableLiveData()
    val itemsLive: LiveData<List<AdapterItems>>
        get() = _itemList

    private lateinit var persons: List<Person>

    init{
        fetchPersons()
    }

    private fun fetchPersons(){
        viewModelScope.launch {
            persons = dataRepository.getPersons()
            val searchPersons = persons.map {
                OrdinaryItem(
                    it.id, it.name, it.description, it.modified, it.thumbnail
                )
            }
            _itemList.postValue(searchPersons)
        }
    }

    fun searchPerson(s: String) {
        val searchPersons = persons.map {
            if (it.name.contains(s, ignoreCase = true)) {
                OrdinaryItem(it.id, it.name, it.description, it.modified, it.thumbnail)
            } else {
                DarkItem(it.id, it.name, it.description, it.modified, it.thumbnail)
            }
        }
        _itemList.postValue(searchPersons)
    }

}


