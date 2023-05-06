package com.example.marvelvm.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class Person(val id: Int,
             val name: String,
             val description:String,
             val modified: String,
             val thumbnail: Thumbnail
): Parcelable{

    @Parcelize
    class Thumbnail(val path: String, val extension: String): Parcelable

}