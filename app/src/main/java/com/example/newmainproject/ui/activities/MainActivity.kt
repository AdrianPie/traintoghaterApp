package com.example.newmainproject.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.newmainproject.R
import com.example.newmainproject.databinding.ActivityMainBinding

import com.example.newmainproject.ui.viewmodel.AuthViewModel
import com.example.newmainproject.utils.DataTransferListener
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), DataTransferListener {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        @Suppress("DEPRECATION")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_container)
                as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_bar_main)

        setupWithNavController(bottomNavigationView, navController)
    }

    override fun onDataTransfer(data: Boolean) {
        if (data) {
            binding.bottomBarMain.visibility = View.VISIBLE
        }
    }
}