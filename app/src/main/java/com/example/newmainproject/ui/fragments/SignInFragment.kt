package com.example.newmainproject.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.newmainproject.R
import com.example.newmainproject.data.firestore.auth.FirebaseAuthRepository
import com.example.newmainproject.databinding.FragmentSignInBinding
import com.example.newmainproject.ui.viewmodel.AuthViewModel
import com.example.newmainproject.utils.DataTransferListener
import java.lang.RuntimeException


class SignInFragment : Fragment(R.layout.fragment_sign_in) {

 private lateinit var binding: FragmentSignInBinding
 private lateinit var authViewModel: AuthViewModel
 private lateinit var createAccount: TextView
 private lateinit var signInButton: Button
 private lateinit var dataTransferListener: DataTransferListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)
        createAccount = binding.tvCreateSingin
        signInButton = binding.btnSignIn2


        authViewModel = AuthViewModel()

        authViewModel.SuccessLogin.observe(viewLifecycleOwner) {
                success ->
            Log.d("ocb", "onViewCreated: ocb2$success")
            if (success) {
                authViewModel.storeUserId()
                val navController = Navigation.findNavController(requireView())
                navController.navigate(R.id.action_signInFragment_to_homeFragment)
               // showToast("Login Success!")
                val showBottomNavigation = true
                dataTransferListener.onDataTransfer(showBottomNavigation)

            } else {
               // showToast("Login Failed!")
            }
        }

        signInButton.setOnClickListener {
            val email = binding.etEmailSingIn.text.toString()
            val password = binding.etPasswordSingIn.text.toString()

            authViewModel.loginUser(email, password)
            Log.d("ocb", "onViewCreated: ocb")

        }


        createAccount.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_singUpFragment)
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
