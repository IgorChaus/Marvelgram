package com.example.marvelvm.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelvm.R
import com.example.marvelvm.model.Person


class RVAdapter() : RecyclerView.Adapter<RVAdapter.PersonViewHolder>() {

    private var  persons: List<Person> = listOf()

    private var strSearch: String = ""

    fun refreshUsers(persons: List<Person>) {
        this.persons = persons
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PersonViewHolder {
        val v: View =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item, viewGroup, false)
        return PersonViewHolder(v)
    }

    override fun onBindViewHolder(personViewHolder: PersonViewHolder, position: Int) {
        val path: String = persons[position].thumbnail.path + "." +
                persons[position].thumbnail.extension
        Glide.with(personViewHolder.itemView.getContext()).load(path)
            .into(personViewHolder.personPhoto)
    }

    override fun getItemCount(): Int {
      return persons.size
    }

    class PersonViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val personPhoto: ImageView = itemView.findViewById(R.id.imageView)
    }

}