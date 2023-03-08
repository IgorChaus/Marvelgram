package com.example.marvelvm.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.kode_viewmodel.source.DataRepository
import com.example.marvelvm.model.Person
import com.example.marvelvm.wrappers.DarkItem
import com.example.marvelvm.wrappers.IRow
import com.example.marvelvm.wrappers.UsualItem
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class AppViewModel(private val dataRepository: DataRepository): ViewModel() {

    // Object must be observable but with a private setter, so we separate LiveData's objects
    private val itemsDataEmitter: MutableLiveData<List<IRow>> = MutableLiveData()
    val itemsLiveData: LiveData<List<IRow>> = itemsDataEmitter
    val aa="Stop"

    lateinit var persons: List<Person>

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
            persons = dataRepository.getPersons()
            var searchPersons: ArrayList<IRow> = arrayListOf()
            persons.forEach {
                searchPersons.add(UsualItem(it.id,it.name,it.description,it.modified,it.thumbnail))
            }
            itemsDataEmitter.postValue(searchPersons)

        }
    }

    fun searchPerson(s: String){
        var searchPersons: ArrayList<IRow> = arrayListOf()
        persons.forEach {
            if (it.name.contains(s,ignoreCase = true)){
                searchPersons.add(UsualItem(it.id,it.name,it.description,it.modified,it.thumbnail))
            }else{
                searchPersons.add(DarkItem(it.id,it.name,it.description,it.modified,it.thumbnail))
            }
        }
        itemsDataEmitter.postValue(searchPersons)
    }

}


