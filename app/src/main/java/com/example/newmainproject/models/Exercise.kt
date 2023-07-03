package com.example.newmainproject.models

import androidx.room.Entity
import androidx.room.PrimaryKey


data class Exercise(
    val id: Int,
    val name: String,
    var duration: Int,
    var breakTime: Int,
    var quantity: Int,
    var image: Int,
    var sets: Int,
    val gifUri: Int){
    constructor() : this(0, "", 0, 0, 0, 0, 0, 0)
}
