package com.example.kode_viewmodel.di

import com.example.marvelvm.di.ApplicationScope
import com.example.marvelvm.view.MainFragment
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class])
interface ApplicationComponent {

    fun inject(fragment: MainFragment)

}