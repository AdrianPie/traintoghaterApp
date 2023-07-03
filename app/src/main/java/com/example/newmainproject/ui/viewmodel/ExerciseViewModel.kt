package com.example.newmainproject.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.newmainproject.data.room.ExerciseDatabase
import com.example.newmainproject.data.room.ExerciseRepository
import com.example.newmainproject.models.Exercise
import com.example.newmainproject.models.ExerciseList
import kotlinx.coroutines.launch

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ExerciseRepository
    private val allExercises: LiveData<List<ExerciseList>>

    init {
        val exerciseDao = ExerciseDatabase.getDatabase(application).exerciseDao()
        repository = ExerciseRepository(exerciseDao)
        allExercises = repository.allExercises
    }

    fun insert(exerciseList: ExerciseList) = viewModelScope.launch {
        repository.insert(exerciseList)
    }

    fun update(exerciseList: ExerciseList) = viewModelScope.launch {
        repository.update(exerciseList)
    }

    fun delete(exerciseList: ExerciseList) = viewModelScope.launch {
        repository.delete(exerciseList)
    }
    fun getAllExercises(): LiveData<List<ExerciseList>> {
        return allExercises
    }
}