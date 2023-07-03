package com.example.newmainproject.ui.viewmodel


import androidx.lifecycle.ViewModel
import com.example.newmainproject.data.images.ImagesRepository

class ImagesViewModel: ViewModel() {
    private val repository: ImagesRepository = ImagesRepository()

    fun returnAllProfileImages(): List<Int> {
        return repository.getProfileImages()
    }

}