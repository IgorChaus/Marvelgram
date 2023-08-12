package com.example.kode_viewmodel.di

import com.example.marvelvm.api.ApiInterface
import com.example.marvelvm.source.RetrofitInstance
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun providePersonApi(): ApiInterface {
        return RetrofitInstance.service
    }

}