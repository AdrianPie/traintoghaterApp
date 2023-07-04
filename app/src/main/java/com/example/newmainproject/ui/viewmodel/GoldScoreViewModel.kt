package com.example.newmainproject.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.newmainproject.data.sharedpreferences.GoldScoreRepository

class GoldScoreViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GoldScoreRepository = GoldScoreRepository(application)

    fun getGoldScore(): Int {
        return repository.getGoldScore()
    }

    fun updateGoldScore(score: Int) {
        repository.updateGoldScore(score)
    }
}