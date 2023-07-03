package com.example.newmainproject.utils

import com.example.newmainproject.R
import com.example.newmainproject.models.Exercise

class Constants {

    companion object {
        const val EMAIL= "email"
        const val USERS = "users"
        const val GROUPS = "groups"
        const val MEMBERS = "members"
        var HAVE_GROUP = false
        var CURRENT_USER_ID: String = "current_user_id"
        fun blankExerciseList(): ArrayList<Exercise>{
            val exerciseList = ArrayList<Exercise>()

            val plank = Exercise(
                1,
                "Plank",
                5,
                4,
                0,
                R.drawable.plank,
                0,
                R.raw.onehandpushup
            )
            val pushUp = Exercise(
                5,
                "Push Up",
                5,
                6,
                0,
                R.drawable.pushup,
                0,
                R.raw.test2
            )
            val sidePlank = Exercise(
                3,
                "Side Plank",
                4,
                5,
                0,
                R.drawable.sideplank,
                0,
                R.raw.test3
            )
            val squat = Exercise(
                2,
                "squat",
                4,
                3,
                0,
                R.drawable.squat,
                0,
                R.raw.onehandpushup
            )
            exerciseList.add(plank)
            exerciseList.add(sidePlank)
            exerciseList.add(pushUp)
            exerciseList.add(squat)
            return exerciseList
        }
    }
}