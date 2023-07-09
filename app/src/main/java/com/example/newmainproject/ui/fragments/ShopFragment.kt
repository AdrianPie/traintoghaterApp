package com.example.newmainproject.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newmainproject.R
import com.example.newmainproject.adapters.PremiumProfilePicturesAdapter
import com.example.newmainproject.adapters.ProfilePicturesAdapter
import com.example.newmainproject.databinding.FragmentShopBinding
import com.example.newmainproject.ui.viewmodel.GoldScoreViewModel
import com.example.newmainproject.ui.viewmodel.ImagesViewModel
import com.example.newmainproject.ui.viewmodel.PremiumProfileViewModel
import com.example.newmainproject.utils.DataTransferListener
import java.lang.RuntimeException


class ShopFragment : Fragment(R.layout.fragment_shop),
    PremiumProfilePicturesAdapter.OnItemClickListener {
    private lateinit var binding: FragmentShopBinding
    private val imagesViewModel by viewModels<ImagesViewModel>()
    private val premiumProfileViewModel by viewModels<PremiumProfileViewModel>()
    private val goldScoreViewModel by viewModels<GoldScoreViewModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var goldScore: TextView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentShopBinding.bind(view)
        goldScore = binding.goldProfil
        goldScore.text = goldScoreViewModel.getGoldScore().toString()
        val boughtList = premiumProfileViewModel.getBooleanList()

        Log.d("szmata", "onViewCreated: ${boughtList.size}")
        for (i in boughtList) {
            Log.d("kurwy", "onViewCreated: $i ")
        }
        binding.toolbarBack.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_shopFragment_to_profilFragment2)
        }

        recyclerView = binding.rvShop
        val adapter = PremiumProfilePicturesAdapter(imagesViewModel.returnAllPremiumProfileImages(), this, boughtList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
    }




    override fun onItemClick(position: Int) {
        Log.d("dupa", "onItemClick: dupa $position")
        val notSold = premiumProfileViewModel.getBooleanValue("boolean_key_$position",false)
        val goldLeft = goldScoreViewModel.getGoldScore()
        val boughtList = premiumProfileViewModel.getBooleanList()
        for (i in boughtList) {
            Log.d("kurwy", "onViewCreated: $i ${boughtList.size}")
        }
        Log.d("kurwy", "onItemClick: $notSold")
        if (!notSold && goldLeft >= 2) {
            goldScoreViewModel.updateGoldScore(-1)
            premiumProfileViewModel.updateBooleanValue("boolean_key_$position",true)
            Toast.makeText(context, "YOU BOUGHT A NEW PROFILE PICTURE", Toast.LENGTH_SHORT).show()
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(position) as PremiumProfilePicturesAdapter.ViewHolder
            goldScore.text = goldScoreViewModel.getGoldScore().toString()
            // Update the button text
            viewHolder.button.text = "BOUGHT" // Replace "New Text" with your desired text
        }
    }




}