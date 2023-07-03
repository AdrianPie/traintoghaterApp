package com.example.newmainproject.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.newmainproject.models.Exercise
import com.example.newmainproject.models.ExerciseList

@Dao
interface ExerciseListDao {
    @Insert
    suspend fun insert(exerciseList: ExerciseList)

    @Update
    suspend fun update(exerciseList: ExerciseList)

    @Delete
    suspend fun delete(exerciseList: ExerciseList)

    @Query("SELECT * FROM exerciseList")
    fun getAllExercises(): LiveData<List<ExerciseList>>
}