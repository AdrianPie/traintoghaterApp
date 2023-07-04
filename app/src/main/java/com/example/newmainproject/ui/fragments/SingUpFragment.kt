package com.example.newmainproject.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.newmainproject.R
import com.example.newmainproject.data.firestore.auth.FirebaseAuthRepository
import com.example.newmainproject.data.firestore.db.FirestoreDatabaseRepository
import com.example.newmainproject.databinding.FragmentSingUpBinding
import com.example.newmainproject.models.User
import com.example.newmainproject.ui.viewmodel.AuthViewModel

import com.example.newmainproject.ui.viewmodel.FirestoreDatabaseViewModel
import com.example.newmainproject.utils.Constants

import com.example.newmainproject.utils.DataTransferListener
import kotlinx.coroutines.delay
import java.lang.RuntimeException


class SingUpFragment : Fragment(R.layout.fragment_sing_up) {

    private lateinit var binding: FragmentSingUpBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var firestoredbViewModel: FirestoreDatabaseViewModel
    private lateinit var navController: NavController
    private lateinit var dataTransferListener: DataTransferListener


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSingUpBinding.bind(view)

        navController = Navigation.findNavController(view)

        firestoredbViewModel = FirestoreDatabaseViewModel()
        authViewModel = AuthViewModel()
        var user = User()
        var email: String = ""
        var password:String = ""


        binding.btnSignUp2.setOnClickListener {
             email = binding.etEmailSingUp.text.toString()
             password = binding.etPasswordSingUp.text.toString()
            val name = binding.etNameSingUp.text.toString()
             user = User(name,email)
            authViewModel.registerUser(email, password)
            }
        firestoredbViewModel.SuccessRegisterToDb.observe(viewLifecycleOwner) {
            success -> if (success) {
            Log.d("kupa", "onViewCreated: jesterm tutajdziwko")
            val showBottomNavigation = true
            dataTransferListener.onDataTransfer(showBottomNavigation)
            navController.navigate(R.id.action_singUpFragment_to_homeFragment)
        }
        }

        authViewModel.Success.observe(viewLifecycleOwner) {
                success -> if (success) {
            Log.d("kupa", "onViewCreated: $user ${Constants.CURRENT_USER_ID}")
            firestoredbViewModel.registerUserToDatabase(user,Constants.CURRENT_USER_ID)
        }
        }

        firestoredbViewModel.Success.observe(viewLifecycleOwner) {
                success -> if (success) {
            Toast.makeText(context, "User created!", Toast.LENGTH_LONG).show()

            val showBottomNavigation = true
            dataTransferListener.onDataTransfer(showBottomNavigation)
            navController.navigate(R.id.action_singUpFragment_to_homeFragment)


        } else {
            Toast.makeText(context, "Failed to create User", Toast.LENGTH_SHORT).show()
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
