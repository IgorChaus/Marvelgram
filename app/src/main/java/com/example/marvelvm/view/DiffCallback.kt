package com.example.marvelvm.view

import androidx.recyclerview.widget.DiffUtil
import com.example.marvelvm.wrappers.DarkItem
import com.example.marvelvm.wrappers.IRow
import com.example.marvelvm.wrappers.OrdinaryItem

class DiffCallback: DiffUtil.ItemCallback<IRow>() {
    override fun areItemsTheSame(oldItem: IRow, newItem: IRow): Boolean {
        return when{
            oldItem is OrdinaryItem && newItem is OrdinaryItem -> {
                oldItem.id == newItem.id
            }
            oldItem is DarkItem && newItem is DarkItem -> {
                oldItem.id == newItem.id
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: IRow, newItem: IRow): Boolean {
        return when{
            oldItem is OrdinaryItem && newItem is OrdinaryItem -> {
                oldItem.equals(newItem)
            }
            oldItem is DarkItem && newItem is DarkItem -> {
                oldItem.equals(newItem)
            }
            else -> false
        }
    }
}