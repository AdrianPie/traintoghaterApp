package com.example.newmainproject.utils

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
}
