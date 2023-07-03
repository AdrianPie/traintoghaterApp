package com.example.newmainproject.data.room

import androidx.lifecycle.LiveData
import com.example.newmainproject.models.Exercise
import com.example.newmainproject.models.ExerciseList

class ExerciseRepository(private val exerciseDao: ExerciseListDao) {
    val allExercises: LiveData<List<ExerciseList>> = exerciseDao.getAllExercises()

    suspend fun insert(exerciseList: ExerciseList) {
        exerciseDao.insert(exerciseList)
    }

    suspend fun update(exerciseList: ExerciseList) {
        exerciseDao.update(exerciseList)
    }

    suspend fun delete(exerciseList: ExerciseList) {
        exerciseDao.delete(exerciseList)
    }
}