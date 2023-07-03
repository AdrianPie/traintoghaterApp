package com.example.newmainproject.data.singleton

import com.example.newmainproject.models.User
import com.example.newmainproject.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object UserSingleton {
    private var user: User? = null

    fun getUser(): User? {
        return user
    }
    fun fetchUser(userSingle: User) {
        if (user == null) {
            user = userSingle
        }
    }
    fun updateUser(updatedUser: User) {
        user = updatedUser
    }

}
