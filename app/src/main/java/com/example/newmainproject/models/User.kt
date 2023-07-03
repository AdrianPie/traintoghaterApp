package com.example.newmainproject.models

data class User(
    val name: String = "",
    val email: String = "",
    var image: Int = 0,
    var presence: Int = 0,
    var group: String = "",
    var haveGroup: Boolean = false)
