package com.example.marvelvm

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.kode_viewmodel.source.DataRepository
import com.example.kode_viewmodel.source.RetrofitInstance
import com.example.marvelvm.view.MainFragment
import com.example.marvelvm.viewmodel.AppViewModel

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val dataRepository = DataRepository(RetrofitInstance.service)
        val factory = AppViewModel.Factory(dataRepository)
        //Создаем экземпляр viewModel в хранилище Activity
        ViewModelProvider(this, factory).get(AppViewModel::class.java)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.getIstance())
                .commitNow()
        }

    }

    //Обрабатываем нажатие стрелки назад
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(supportFragmentManager.getBackStackEntryCount() > 0)
            supportFragmentManager.popBackStack()

        return super.onOptionsItemSelected(item)
    }

}