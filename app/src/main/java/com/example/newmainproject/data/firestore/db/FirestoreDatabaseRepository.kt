package com.example.newmainproject.data.firestore.db

import android.util.Log
import android.widget.Toast
import com.example.newmainproject.models.ExerciseList

import com.example.newmainproject.models.Group
import com.example.newmainproject.models.User
import com.example.newmainproject.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import kotlin.Exception

class FirestoreDatabaseRepository {
    private val firestore = FirebaseFirestore.getInstance()


    suspend fun registerUserToDatabase(user: User, id: String): Boolean{
        return try {
            firestore.collection(Constants.USERS)
                .document(id)
                .set(user).await()
            true
        } catch (e: Exception) {
            Log.e("Error with registration", "registerUserToDatabase: ${e.message}", )
            false
        }
    }

    suspend fun registerNewGroup(group: Group): Boolean {
        group.readyListL.add(false)
        group.members.add(Constants.CURRENT_USER_ID)
        return try {
            firestore.collection(Constants.GROUPS)
                .document(group.name)
                .set(group).addOnSuccessListener {  }.addOnFailureListener {  }.await()
            true
        } catch (e : Exception) {
            Log.e("Error with group registration", "registerNewGroup: ${e.message}", )
            false
        }
    }
    suspend fun getAllMembers(groupMembers: ArrayList<String>): ArrayList<User>? {
        var userList = ArrayList<User>()
        return try {
            for(i in groupMembers) {
                Log.d("allmembers", "getAllMembers: $i")
                val doc = firestore.collection(Constants.USERS)
                    .document(i)
                    .get().await()
                val user = doc.toObject(User::class.java)
                userList.add(user!!)
            }
            userList
        } catch (e: Exception) {
            Log.d("allmembers", "getAllMembers: $e")
            null
        }
    }

    suspend fun getGroup(groupName: String): Group? {
        return try {

            val doc = firestore.collection(Constants.GROUPS)
                .document(groupName)
                .get().await()
            val group: Group? = doc.toObject(Group::class.java)
            group!!
        } catch (e: Exception) {
            Log.d("dupa", "getGroup1: $e")
            null
        }
    }
    suspend fun registerUserToGroup(name: String, password: String): Boolean {
        return try {
           val doc = firestore.collection(Constants.GROUPS)
                .document(name)
                .get().await()
            val group: Group? = doc.toObject(Group::class.java)
            if (group?.password == password) {
                group.readyListL.add(false)
                group.members.add(Constants.CURRENT_USER_ID)
                firestore.collection(Constants.GROUPS)
                    .document(name)
                    .set(group)
                    .await()
            } else {
                Log.d("kupa", "registerUserToGroup: ZLE HASLO")
                return false
            }
            true
        } catch (e: Exception) {
            Log.e("Error", "registerUserToGroup: ${e.message}", )
            false
        }
    }
    suspend fun updateGroup(
        image: Int? = null,
        member: String? = null,
        name: String,
        nextExercise: ExerciseList? = null,
        password: String? = null,
        readyList: ArrayList<Boolean>? = null): Boolean {
        return try {
            val doc = firestore.collection(Constants.GROUPS)
                .document(name)
                .get().await()
            var group = doc.toObject(Group::class.java)
            if (readyList != null) {
                group!!.readyListL = readyList
            }
            if (image != null) {
                group!!.image = image
            }
            if (member!= null && group?.password == password){
                    group!!.members.add(member)

            }
            if (nextExercise != null) {
                group!!.nextExercise = nextExercise
            }
            if (group != null) {
                firestore.collection(Constants.GROUPS)
                    .document(name)
                    .set(group)
                    .await()
            }
            true
        } catch (e: Exception) {
            false
        }
    }
    suspend fun updateUserInfo(image: Int? = null, haveGroup: Boolean? = null, group: String? = null): Boolean {
        return try {
            val doc = firestore.collection(Constants.USERS)
                .document(Constants.CURRENT_USER_ID)
                .get().await()
            val user = doc.toObject(User::class.java)
            if (image != null) {
                user!!.image = image
            }
            if (group!= null){
                user!!.group = group
            }
            if (haveGroup != null) {
                user!!.haveGroup = haveGroup
            }
            if (user != null) {
                firestore.collection(Constants.USERS)
                    .document(Constants.CURRENT_USER_ID)
                    .set(user).await()
            }

            true
        } catch (e: Exception) {
            false
        }
    }
    suspend fun getUserInfo(): User? {
        return try {
            Log.d("dupa", "onViewCreated672: ${Constants.CURRENT_USER_ID}")
            val doc = firestore.collection(Constants.USERS)
                .document(Constants.CURRENT_USER_ID)
                .get().await()
            val user = doc.toObject(User::class.java)
            user
        } catch (e:Exception) {
            null
        }
    }
    fun addGroupSnapshotListener(groupName: String, callback: (Group) -> Unit): ListenerRegistration {
        val docRef = firestore.collection(Constants.GROUPS).document(groupName)

        return docRef.addSnapshotListener { snapshot, error ->
            error?.let {
                Log.d("testy", "addGroupSnapshotListener: ${it.message}")
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
            val group = snapshot.toObject(Group::class.java)

            group?.let { callback(it) }
        }
        }
        }
    }




fun getCurrentUserId(): String {
    val user = FirebaseAuth.getInstance().currentUser
    var userId: String = ""
    if (user != null) {
        userId = user!!.uid
    }
    return  userId
}