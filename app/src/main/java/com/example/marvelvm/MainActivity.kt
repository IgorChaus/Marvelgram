package com.example.marvelvm

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.GridLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kode_viewmodel.source.DataRepository
import com.example.kode_viewmodel.source.RetrofitInstance
import com.example.marvelvm.model.Person
import com.example.marvelvm.view.PhotoActivity
import com.example.marvelvm.view.RVAdapter
import com.example.marvelvm.view.SpecialLayout
import com.example.marvelvm.viewmodel.AppViewModel

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity(), RVAdapter.ItemClickListener {

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

  //      val llm = GridLayoutManager(this, 3)
        rv.layoutManager = llm
        val adapter = RVAdapter(this)
        rv.adapter = adapter

        viewModel.itemsLiveData.observe(this) {
            adapter.refreshUsers(it)
        }

        val editText: EditText = findViewById(R.id.editText)
        editText.addTextChangedListener {
                s -> viewModel.searchPerson(s.toString())
        }

    }

    override fun onItemClick(item: Person){
        val intent = Intent(this, PhotoActivity::class.java)


        intent.putExtra("photo", item.thumbnail.path + "." + item.thumbnail.extension)
        intent.putExtra("description", item.description)
        intent.putExtra("name", item.name)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

        startActivity(intent)

    }


}