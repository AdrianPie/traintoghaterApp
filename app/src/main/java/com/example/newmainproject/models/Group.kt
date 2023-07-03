package com.example.newmainproject.models

import com.example.newmainproject.utils.Constants

data class Group(
    val name: String = "",
    val password: String = "",
    var image: Int = 0,
    var presence: Int = 0,
    var members: ArrayList<String> = ArrayList(),
    var readyListL: ArrayList<Boolean> = ArrayList(),
    var nextExercise: ExerciseList? = null
) {

    constructor() : this(
        "",
        "",
        0,
        0,
        ArrayList(),
        ArrayList(),
        ExerciseList(1,"dupa","sds",Constants.blankExerciseList())
    )
}