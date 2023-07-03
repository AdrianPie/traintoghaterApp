package com.example.newmainproject.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newmainproject.data.singleton.GroupSingleton
import com.example.newmainproject.data.singleton.UserSingleton
import com.example.newmainproject.models.Group
import com.example.newmainproject.models.User
import kotlinx.coroutines.launch

class GroupSingletonViewModel : ViewModel() {

    private val _groupLiveData = MutableLiveData<Group>()
    val groupLiveData: LiveData<Group> get() = _groupLiveData

    fun fetchGroup(groupId: Group) {
        viewModelScope.launch {
            GroupSingleton.fetchGroup(groupId)
            val group = GroupSingleton.getGroup()
            _groupLiveData.postValue(group!!)
        }
    }

    fun updateGroup(updatedGroup: Group) {
        GroupSingleton.updateGroup(updatedGroup)
        _groupLiveData.postValue(updatedGroup)
    }

    fun getGroup(): Group? {
        return GroupSingleton.getGroup()
    }
}




