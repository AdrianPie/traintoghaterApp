package com.example.newmainproject.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newmainproject.data.firestore.auth.FirebaseAuthRepository
import com.example.newmainproject.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class AuthViewModel(): ViewModel() {
    private val _Success = MutableLiveData<Boolean>()
    val Success: LiveData<Boolean> get() = _Success
    private val _SuccessLogin = MutableLiveData<Boolean>()
    val SuccessLogin: LiveData<Boolean> get() = _SuccessLogin
    private val authRepository: FirebaseAuthRepository = FirebaseAuthRepository()

    fun registerUser(email:String, password:String){
        viewModelScope.launch(Dispatchers.IO) {
            val isRegistered = authRepository.registerUser(email, password)
            _Success.postValue(isRegistered)

        }
    }
    fun loginUser(email: String,password: String){
        viewModelScope.launch(Dispatchers.IO) {
            val isSingedUp = authRepository.loginUser(email, password)
            Log.d("kupa", "loginUser: udalo sie2")
            _SuccessLogin.postValue(isSingedUp)
        }
    }
    fun checkIfUserIsSignedIn()  {
        viewModelScope.launch(Dispatchers.IO) {
            val isSingedIn = authRepository.checkIfUserIsSignedIn()
            _Success.postValue(isSingedIn)
        }
    }
    fun storeUserId(){
        viewModelScope.launch(Dispatchers.IO) {

            var userId = authRepository.getUserId()
            Log.d("dupa", "storeUserId: $userId")
            Constants.CURRENT_USER_ID = userId!!
            Log.d("dupa", "storeUserIdConstans: ${Constants.CURRENT_USER_ID}")
        }
    }

}