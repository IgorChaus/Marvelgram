package com.example.marvelvm

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.marvelvm.view.MainFragment

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.getInstance())
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