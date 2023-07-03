package com.example.newmainproject.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newmainproject.models.Exercise
import com.example.newmainproject.models.ExerciseList
import com.example.newmainproject.utils.ListConverter


@Database(entities = [ExerciseList::class], version = 1, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class ExerciseDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseListDao

    companion object {
        @Volatile
        private var INSTANCE: ExerciseDatabase? = null

        fun getDatabase(context: Context): ExerciseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExerciseDatabase::class.java,
                    "exerciseList_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}