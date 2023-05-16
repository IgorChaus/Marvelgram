package com.example.marvelvm.view

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelvm.R
import com.example.marvelvm.model.Person
import com.example.marvelvm.wrappers.DarkItem
import com.example.marvelvm.wrappers.IRow
import com.example.marvelvm.wrappers.LightItem

class RVAdapter: ListAdapter<IRow, PersonViewHolder>(DiffCallback()) {

    var itemClickListener: ((Person) -> Unit)? = null

    private val itemSize = Resources.getSystem().displayMetrics.widthPixels / 3

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is LightItem -> ITEM_LIGHT
            is DarkItem -> ITEM_DARK
            else -> throw IllegalArgumentException()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = when (viewType) {
            ITEM_LIGHT -> LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_light, parent, false)

            ITEM_DARK -> LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_dark, parent, false)
            else -> throw IllegalArgumentException()
        }
        view.layoutParams.width = itemSize
        view.layoutParams.height = itemSize
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(personViewHolder: PersonViewHolder, position: Int) {
        val path: String = (getItem(position) as Person).thumbnail.path + "." +
                (getItem(position) as Person).thumbnail.extension
        Glide.with(personViewHolder.itemView.context).load(path)
            .into(personViewHolder.personPhoto)
        personViewHolder.itemView.setOnClickListener {
            if (personViewHolder.adapterPosition == RecyclerView.NO_POSITION) {
                return@setOnClickListener
            }
            itemClickListener?.invoke(getItem(position) as Person)
        }
    }

    companion object{
        private const val ITEM_LIGHT = 100
        private const val ITEM_DARK = 101
    }
}