package com.example.newmainproject.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.newmainproject.data.sharedpreferences.PremiumProfileRepository

class PremiumProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PremiumProfileRepository = PremiumProfileRepository(application)


    fun getBooleanValue(key: String, defaultValue: Boolean): Boolean {
        return repository.getBooleanValue(key, defaultValue)
    }

    fun updateBooleanValue(key: String, value: Boolean) {
        repository.updateBooleanValue(key, value)
    }
    fun getBooleanList(): ArrayList<Boolean> {
        return repository.getBooleanList()
    }
}
