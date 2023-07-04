package com.example.newmainproject.data.sharedpreferences

import android.content.Context

class GoldScoreRepository(private val context: Context) {

    private val sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun getGoldScore(): Int {
        return sharedPref.getInt("goldscore", 0)
    }

    fun updateGoldScore(score: Int) {
        val currentScore = getGoldScore()
        val updatedScore = currentScore + score
        val editor = sharedPref.edit()
        editor.putInt("goldscore", updatedScore)
        editor.apply()
    }
}