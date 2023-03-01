package com.example.marvelvm

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.kode_viewmodel.source.DataRepository
import com.example.kode_viewmodel.source.RetrofitInstance
import com.example.marvelvm.view.RVAdapter
import com.example.marvelvm.view.SpecialLayout
import com.example.marvelvm.viewmodel.AppViewModel

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        val dataRepository = DataRepository(RetrofitInstance.service)
        val factory = AppViewModel.Factory(dataRepository)
        val viewModel by lazy {ViewModelProvider(this,factory)
            .get(AppViewModel::class.java)}

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar: ActionBar? = getSupportActionBar()
        actionBar?.setBackgroundDrawable(
            ColorDrawable(
            ContextCompat
                .getColor(this, R.color.black))
        )
        actionBar?.setIcon(R.drawable.marvel)
        actionBar?.setDisplayShowHomeEnabled(true)
        setTitle("")

        val rv: RecyclerView = findViewById(R.id.rv1)
        val llm = SpecialLayout(this)

        rv.layoutManager = llm
        val adapter = RVAdapter()
        rv.adapter = adapter

        viewModel.itemsLiveData.observe(this) {
            adapter.refreshUsers(it)
        }



       /* val editText: EditText = findViewById(R.id.editText)
        editText.addTextChangedListener {
                s -> rv.adapter = null
            rv.layoutManager = null
            rv.adapter = adapter
            rv.layoutManager = llm
            adapter.setMovieList(persons,s.toString())
        }*/

    }
}