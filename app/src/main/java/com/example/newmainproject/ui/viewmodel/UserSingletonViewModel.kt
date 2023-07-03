package com.example.newmainproject.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newmainproject.data.singleton.UserSingleton
import com.example.newmainproject.models.User
import kotlinx.coroutines.launch

class UserSingletonViewModel: ViewModel() {

    private val _userLiveData = MutableLiveData<User>()
    val userLiveData: LiveData<User> get() = _userLiveData

    fun fetchUser(userId: User) {
        viewModelScope.launch {
            UserSingleton.fetchUser(userId)
            val user = UserSingleton.getUser()
            _userLiveData.postValue(user!!)
        }
    }
    fun updateUser(updatedUser: User) {
        UserSingleton.updateUser(updatedUser)
        _userLiveData.postValue(updatedUser)
    }

    fun getUser(): User? {
        return UserSingleton.getUser()
    }
}