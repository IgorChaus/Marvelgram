package com.example.marvelvm.model

open class Person(val id: Int,
             val name: String,
             val description:String,
             val modified: String,
             val thumbnail: Thumbnail
){

    class Thumbnail(val path: String, val extension: String)

}