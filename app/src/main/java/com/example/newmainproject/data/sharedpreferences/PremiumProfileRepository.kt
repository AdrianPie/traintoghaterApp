package com.example.newmainproject.data.sharedpreferences

import android.content.Context
import android.util.Log

class PremiumProfileRepository(private val context: Context) {

    private val sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    init {
        // Check if the booleans are not already set
        if (!sharedPref.contains("boolean_key_1")) {
            val editor = sharedPref.edit()
            editor.putBoolean("boolean_key_0", false)
            editor.putBoolean("boolean_key_1", false)
            editor.putBoolean("boolean_key_2", false)
            editor.putBoolean("boolean_key_3", false)
            editor.putBoolean("boolean_key_4", false)
            editor.putBoolean("boolean_key_5", false)
            editor.apply()
        }
    }

    fun getBooleanValue(key: String, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(key, defaultValue)
    }

    fun updateBooleanValue(key: String, value: Boolean) {
        val editor = sharedPref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }
    fun getBooleanList(): ArrayList<Boolean> {
        val booleanList = ArrayList<Boolean>()
        booleanList.add(sharedPref.getBoolean("boolean_key_0", false))
        Log.d("kurwa1", "getBooleanList: ${booleanList[0]}")
        booleanList.add(sharedPref.getBoolean("boolean_key_1", false))
        Log.d("kurwa2", "getBooleanList: ${booleanList[1]}")
        booleanList.add(sharedPref.getBoolean("boolean_key_2", false))
        Log.d("kurwa3", "getBooleanList: ${booleanList[2]}")
        booleanList.add(sharedPref.getBoolean("boolean_key_3", false))
        Log.d("kurwa4", "getBooleanList: ${booleanList[3]}")
        booleanList.add(sharedPref.getBoolean("boolean_key_4", false))
        Log.d("kurwa5", "getBooleanList:${booleanList[4]} ")
        booleanList.add(sharedPref.getBoolean("boolean_key_5", false))
        Log.d("kurwa6", "getBooleanList: ${booleanList[5]} ${booleanList.size} ")
        return booleanList
    }
}