package com.example.newmainproject.data.images

import com.example.newmainproject.R

class ImagesRepository {
     fun getProfileImages(): ArrayList<Int>{
        return arrayListOf(
            R.drawable.bambi,
            R.drawable.bearone,
            R.drawable.beartwo,
            R.drawable.beer,
            R.drawable.bulbalike,
            R.drawable.catfour,
            R.drawable.catone,
            R.drawable.catthree,
            R.drawable.cattwo,
            R.drawable.elephantone,
            R.drawable.elephanttwo,
            R.drawable.elfgirl,
            R.drawable.lionone,
            R.drawable.orcone,
            R.drawable.orctwo,
            R.drawable.robotone,
            R.drawable.santa,
            R.drawable.add_photo_24
        )
    }
    fun getPremiumProfileImages(): ArrayList<Int>{
        return arrayListOf(
            R.drawable.shopone,
            R.drawable.shoptwo,
            R.drawable.shopthree,
            R.drawable.shopfour,
            R.drawable.shopfive,
            R.drawable.shopsix,
        )
    }
}