package com.example.newmainproject.data.singleton

import com.example.newmainproject.models.Group
import com.example.newmainproject.models.User

object GroupSingleton {
    private var group: Group? = null

    fun getGroup(): Group? {
        return group
    }
    fun fetchGroup(groupSingle: Group) {
        if (group == null) {
            group = groupSingle
        }
    }
    fun updateGroup(updatedGroup: Group) {
        group = updatedGroup
    }

}
