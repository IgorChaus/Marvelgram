package com.example.marvelvm.wrappers

import com.example.marvelvm.model.Person

interface AdapterItems

class OrdinaryItem(id: Int,
                   name: String,
                   description: String,
                   modified: String,
                   thumbnail: Thumbnail,
) : Person(id, name, description, modified, thumbnail),
    AdapterItems


class DarkItem(id: Int,
                name: String,
                description: String,
                modified: String,
                thumbnail: Thumbnail,
) : Person(id, name, description, modified, thumbnail),
    AdapterItems

