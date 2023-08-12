package com.example.marvelvm

import android.app.Application
import com.example.kode_viewmodel.di.DaggerApplicationComponent

class MarvelApp: Application() {
    val component by lazy{
        DaggerApplicationComponent.create()
    }
}