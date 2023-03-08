package com.example.marvelvm.view

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelvm.R
import com.example.marvelvm.model.Person

@RequiresApi(Build.VERSION_CODES.O)
class PhotoActivity: AppCompatActivity(), RVAdapter.ItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_item)

        window.setBackgroundDrawable(ContextCompat
            .getDrawable(this, R.color.black))

        val photo: String? = getIntent().getExtras()!!.getString("photo")
        val name: String? = getIntent().getExtras()!!.getString("name")
        val description: String? = getIntent().getExtras()!!.getString("description")

        //Выводим стрелочку "Назад"
        val actionBar = getSupportActionBar()
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat
                .getColor(this, R.color.black)))

        actionBar?.setTitle(name)

        val imagePhoto: ImageView = findViewById(R.id.imagePhoto)
        Glide.with(this).load(photo).into(imagePhoto)

        val textView: TextView = findViewById(R.id.textView)
        textView.text = description

        val rv: RecyclerView = findViewById(R.id.rv2)
        val llm = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv.layoutManager = llm
        val adapter = RVAdapter(this)
        rv.adapter = adapter
    }

    //Обработка нажатия стрелки назад.
    //Так как это у нас единственный пункт меню, то нет необходимости его проверять
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        this.finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(item: Person){
        actionBar?.setTitle(item.name)

        val imagePhoto: ImageView = findViewById(R.id.imagePhoto)
        val photo = item.thumbnail.path + "." + item.thumbnail.extension
        Glide.with(this).load(photo).into(imagePhoto)

        val textView: TextView = findViewById(R.id.textView)
        textView.text = item.description

    }
}
