package com.example.newmainproject.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newmainproject.data.firestore.db.FirestoreDatabaseRepository
import com.example.newmainproject.models.ExerciseList
import com.example.newmainproject.models.Group
import com.example.newmainproject.models.User
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FirestoreDatabaseViewModel() : ViewModel() {
    private val firestore: FirestoreDatabaseRepository = FirestoreDatabaseRepository()
    private val _Success = MutableLiveData<Boolean>()
    val Success: LiveData<Boolean> get() = _Success

    private val _SuccessRegisterToDb = MutableLiveData<Boolean>()
    val SuccessRegisterToDb: LiveData<Boolean> get() = _SuccessRegisterToDb

    private val _SuccessGetGroup= MutableLiveData<Group>()
    val SuccessGetGroup: LiveData<Group> get() = _SuccessGetGroup

    private val _SuccessGetListenerGroup= MutableLiveData<Group>()
    val SuccessGetListenerGroup: LiveData<Group> get() = _SuccessGetListenerGroup

    private val _SuccessGetAllMembers = MutableLiveData<ArrayList<User>>()
    val SuccessGetAllMembers: LiveData<ArrayList<User>> get() = _SuccessGetAllMembers

    private var groupListenerRegistration: ListenerRegistration? = null

    private val _userInfo = MutableLiveData<User>()
    val userInfo: LiveData<User> get() = _userInfo

    fun registerUserToDatabase(user:User, id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isRegistered = firestore.registerUserToDatabase(user,id)
            Log.d("kupa", "registerUserToDatabase: kupa222")
            _SuccessRegisterToDb.postValue(isRegistered)
        }
    }
    fun registerGroup(group: Group) {
        viewModelScope.launch(Dispatchers.IO) {
            val isRegistered = firestore.registerNewGroup(group)
            _Success.postValue(isRegistered)
        }
    }
    fun registerUserToGroup(name:String,password:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isRegistered = firestore.registerUserToGroup(name, password)
            _Success.postValue(isRegistered)
        }
    }
    fun updateGroup(
        image: Int? = null,
        member: String? = null,
        name: String,
        nextExercise: ExerciseList? = null,
        password: String? = null,
        readyList: ArrayList<Boolean>? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val isRegistered = firestore.updateGroup(image, member, name, nextExercise, password,readyList)
            _Success.postValue(isRegistered)
        }
    }
    fun updateUserInfo(image: Int? = null, haveGroup: Boolean? = null, group: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val isChanged = firestore.updateUserInfo(image, haveGroup, group)
            _Success.postValue(isChanged)
        }
    }
    fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO){
            val user = firestore.getUserInfo()
            Log.d("dupa", "getUserInfo23: ${user!!.name} ")
            _userInfo.postValue(user!!)
        }
    }
    fun getGroup(groupName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val group = firestore.getGroup(groupName)
            _SuccessGetGroup.postValue(group!!)
        }
    }
    fun getAllMembers(groupMembers: ArrayList<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            val members = firestore.getAllMembers(groupMembers)
            _SuccessGetAllMembers.postValue(members!!)
        }
    }
    fun addGroupSnapshotListener(groupName: String, callback: (Group) -> Unit) {
        // Remove existing listener if any
        groupListenerRegistration?.remove()

        groupListenerRegistration = firestore.addGroupSnapshotListener(groupName) { updatedGroup ->
            Log.d("tutaj", "addGroupSnapshotListener: jestem tutaj1")
            _SuccessGetListenerGroup.postValue(updatedGroup)
        }
    }
    override fun onCleared() {
        super.onCleared()
        // Remove listener when ViewModel is cleared
        groupListenerRegistration?.remove()
    }



}