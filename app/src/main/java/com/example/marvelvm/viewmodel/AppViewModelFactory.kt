package com.example.marvelvm.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marvelvm.di.ApplicationScope
import com.example.marvelvm.source.DataRepository
import javax.inject.Inject

@ApplicationScope
@Suppress("UNCHECKED_CAST")
class AppViewModelFactory @Inject constructor(
    private val dataRepository: DataRepository
    ) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java))
            return AppViewModel(dataRepository) as T
        throw RuntimeException("Unknown ViewModel class $modelClass")
    }
}