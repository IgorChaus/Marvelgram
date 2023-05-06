package com.example.marvelvm.view


import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelvm.R
import com.example.marvelvm.model.Person
import com.example.marvelvm.wrappers.DarkItem
import com.example.marvelvm.wrappers.IRow
import com.example.marvelvm.wrappers.UsualItem


class RVAdapter: ListAdapter<IRow, RVAdapter.PersonViewHolder>(DiffCallback()) {

    var itemClickListener: ((Person) -> Unit)? = null

    private val itemSize = Resources.getSystem().displayMetrics.widthPixels / 3

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is UsualItem -> R.layout.item
            is DarkItem -> R.layout.item_dark
            else -> throw IllegalArgumentException()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = when (viewType) {
            R.layout.item -> LayoutInflater.from(parent.context)
                    .inflate(R.layout.item, parent, false)

            R.layout.item_dark -> LayoutInflater.from(parent.context)
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

    class PersonViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val personPhoto: ImageView = itemView.findViewById(R.id.imageView)
    }

}