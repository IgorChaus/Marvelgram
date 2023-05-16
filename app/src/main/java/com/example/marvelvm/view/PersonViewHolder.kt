package com.example.marvelvm.view

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelvm.R

class PersonViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val personPhoto: ImageView = itemView.findViewById(R.id.imageView)
}