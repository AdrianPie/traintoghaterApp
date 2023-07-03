package com.example.newmainproject.data.firestore.auth

import android.util.Log
import com.example.newmainproject.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class FirebaseAuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun registerUser(email: String, password: String): Boolean {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Constants.CURRENT_USER_ID = auth.uid!!
            true
        } catch (e: Exception) {
            Log.e("AuthError", "loginUser: ${e.message}", )
            false
        }
    }
    suspend fun loginUser(email: String,password: String):Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Constants.CURRENT_USER_ID = auth.uid!!
            Log.d("kupa", "loginUser: udalo sie")
            true
        } catch (e: Exception) {
            Log.e("AuthError2", "loginUser: ${e.message}", )
            false
        }
    }
     suspend fun getUserId(): String? {
        val currentUser = auth.currentUser
         Log.d("dupa", "getUserId1: ${currentUser!!.uid}")
        return currentUser?.uid

    }
     suspend fun checkIfUserIsSignedIn(): Boolean {
        return auth.currentUser != null
    }
}