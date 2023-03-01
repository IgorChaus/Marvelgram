package com.example.marvelvm.view

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelvm.MainActivity
import com.example.marvelvm.R

class PhotoActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        window.setBackgroundDrawable(ContextCompat
            .getDrawable(this, R.color.black))

        val data: Int = getIntent().getExtras()!!.getInt("photo")

        //Выводим стрелочку "Назад"
        val actionBar = getSupportActionBar()
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat
                .getColor(this, R.color.black)))
/*        actionBar?.setTitle(MainActivity.persons[data].name)

        val imagePhoto: ImageView = findViewById<ImageView>(R.id.imagePhoto)
        val path: String = (MainActivity.persons[data].thumbnail.path + "."
                + MainActivity.persons[data].thumbnail.extension)
        Glide.with(this).load(path).into(imagePhoto)
        val textView: TextView = findViewById<TextView>(R.id.textView)
        textView.setText(MainActivity.persons[data].description)*/
        val rv: RecyclerView = findViewById<RecyclerView>(R.id.rv2)
        val llm = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL,
            false
        )
        rv.layoutManager = llm
        val adapter = RVAdapter()
        rv.adapter = adapter
    }

    //Обработка нажатия стрелки назад.
    //Так как это у нас единственный пункт меню, то нет необходимости его проверять
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        this.finish()
        return super.onOptionsItemSelected(item)
    }
}