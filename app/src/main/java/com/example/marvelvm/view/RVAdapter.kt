package com.example.marvelvm.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelvm.R
import com.example.marvelvm.model.Person
import com.example.marvelvm.wrappers.DarkItem
import com.example.marvelvm.wrappers.IRow
import com.example.marvelvm.wrappers.UsualItem


class RVAdapter(private val itemClickListener: ItemClickListener)
    : RecyclerView.Adapter<RVAdapter.PersonViewHolder>() {

    interface ItemClickListener {
        fun onItemClick(item: Person)
    }

    private var  persons: List<IRow> = listOf()


    fun refreshUsers(persons: List<IRow>) {
        this.persons = persons
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int =
        when (persons[position]) {
            is UsualItem -> R.layout.item
            is DarkItem -> R.layout.item_dark
            else -> throw IllegalArgumentException()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        R.layout.item -> PersonViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false))
        R.layout.item_dark -> PersonViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_dark, parent, false))
        else -> throw IllegalArgumentException()
    }


    override fun onBindViewHolder(personViewHolder: PersonViewHolder, position: Int) {
        val path: String = (persons[position] as Person).thumbnail.path + "." +
                (persons[position] as Person).thumbnail.extension
        Glide.with(personViewHolder.itemView.getContext()).load(path)
            .into(personViewHolder.personPhoto)
        personViewHolder.itemView.setOnClickListener {
            if (personViewHolder.getAdapterPosition() == RecyclerView.NO_POSITION) {
                return@setOnClickListener
            }
            itemClickListener.onItemClick(persons[position] as Person)
        }
    }

    override fun getItemCount(): Int {
      return persons.size
    }

    class PersonViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val personPhoto: ImageView = itemView.findViewById(R.id.imageView)
    }

}