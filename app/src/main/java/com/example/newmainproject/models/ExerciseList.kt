package com.example.newmainproject.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exerciseList")
class ExerciseList(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    var date: String,
    val exerciseList: ArrayList<Exercise>
) {
    constructor() : this(0, "", "", ArrayList())

}