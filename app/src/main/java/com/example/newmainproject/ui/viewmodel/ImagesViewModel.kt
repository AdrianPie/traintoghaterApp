package com.example.newmainproject.ui.viewmodel


import androidx.lifecycle.ViewModel
import com.example.newmainproject.data.images.ImagesRepository

class ImagesViewModel: ViewModel() {
    private val repository: ImagesRepository = ImagesRepository()

    fun returnAllProfileImages(): ArrayList<Int> {
        return repository.getProfileImages()
    }
    fun returnAllPremiumProfileImages(): ArrayList<Int> {
        return repository.getPremiumProfileImages()
    }

}