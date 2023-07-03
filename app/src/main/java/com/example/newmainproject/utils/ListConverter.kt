package com.example.newmainproject.utils

import androidx.room.TypeConverter
import com.example.newmainproject.models.Exercise
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter {

        private val gson = Gson()

        @TypeConverter
        fun fromList(value: ArrayList<Exercise>): String {
            return gson.toJson(value)
        }

        @TypeConverter
        fun toList(value: String): ArrayList<Exercise> {
            val listType = object : TypeToken<ArrayList<Exercise>>() {}.type
            return gson.fromJson(value, listType)
        }

}