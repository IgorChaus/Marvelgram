package com.example.marvelvm.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.marvelvm.model.Person
import com.example.marvelvm.source.DataRepository
import com.example.marvelvm.wrappers.DarkItem
import com.example.marvelvm.wrappers.IRow
import com.example.marvelvm.wrappers.OrdinaryItem
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class AppViewModel(private val dataRepository: DataRepository): ViewModel() {

    private val _itemList: MutableLiveData<List<IRow>> = MutableLiveData()
    val itemsLive: LiveData<List<IRow>>
        get() = _itemList

    private lateinit var persons: List<Person>

    init{
        fetchPersons()
    }

    private fun fetchPersons(){
        viewModelScope.launch {
            persons = dataRepository.getPersons()
            val searchPersons: ArrayList<IRow> = arrayListOf()
            persons.forEach {
                searchPersons.add(OrdinaryItem(it.id,it.name,it.description,it.modified,it.thumbnail))
            }
            _itemList.postValue(searchPersons)

        }
    }

    fun searchPerson(s: String){
        val searchPersons: ArrayList<IRow> = arrayListOf()
        persons.forEach {
            if (it.name.contains(s,ignoreCase = true)){
                searchPersons.add(OrdinaryItem(it.id,it.name,it.description,it.modified,it.thumbnail))
            }else{
                searchPersons.add(DarkItem(it.id,it.name,it.description,it.modified,it.thumbnail))
            }
        }
        _itemList.postValue(searchPersons)
    }

}


