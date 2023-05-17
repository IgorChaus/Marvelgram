package com.example.marvelvm.wrappers

import com.example.marvelvm.model.Person

interface IRow

class OrdinaryItem(id: Int,
                   name: String,
                   description: String,
                   modified: String,
                   thumbnail: Thumbnail,
) : Person(id, name, description, modified, thumbnail),
    IRow


class DarkItem(id: Int,
                name: String,
                description: String,
                modified: String,
                thumbnail: Thumbnail,
) : Person(id, name, description, modified, thumbnail),
    IRow

