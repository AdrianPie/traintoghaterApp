package com.example.newmainproject.ui.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.newmainproject.R
import com.example.newmainproject.data.singleton.UserSingleton
import com.example.newmainproject.ui.viewmodel.AuthViewModel
import com.example.newmainproject.ui.viewmodel.FirestoreDatabaseViewModel
import com.example.newmainproject.ui.viewmodel.GroupSingletonViewModel
import com.example.newmainproject.utils.Constants

import com.example.newmainproject.utils.DataTransferListener
import kotlinx.coroutines.coroutineScope
import java.lang.RuntimeException


class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var navController: NavController
    private lateinit var authViewModel: AuthViewModel
    private lateinit var dbViewModel: FirestoreDatabaseViewModel
    private lateinit var dataTransferListener: DataTransferListener
    private val groupSingletonViewModel by viewModels<GroupSingletonViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        dbViewModel = FirestoreDatabaseViewModel()

        authViewModel.checkIfUserIsSignedIn()

        dbViewModel.SuccessGetGroup.observe(viewLifecycleOwner) {
            group ->
            groupSingletonViewModel.fetchGroup(group)
        }
        dbViewModel.userInfo.observe(viewLifecycleOwner) {
            user -> dbViewModel.getGroup(user.group)
        }
        authViewModel.Success.observe(viewLifecycleOwner) { isSignedIn ->
            if (isSignedIn) {
                authViewModel.storeUserId()
                dbViewModel.getUserInfo()

                Handler(Looper.getMainLooper()).postDelayed({
                        navController.navigate(R.id.action_splashFragment_to_homeFragment)
                        val showBottomNavigation = true
                        dataTransferListener.onDataTransfer(showBottomNavigation)
                    }, 3000)

            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                   navController.navigate(R.id.action_splashFragment_to_signInFragment)
                }, 3000)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DataTransferListener) {
            dataTransferListener = context
        } else {
            throw RuntimeException("$context")
        }
    }
}