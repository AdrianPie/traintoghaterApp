package com.example.newmainproject.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

import android.view.View

import android.widget.Toast
import androidx.navigation.Navigation
import com.example.newmainproject.R
import com.example.newmainproject.databinding.FragmentCreateGroupBinding
import com.example.newmainproject.models.Group
import com.example.newmainproject.ui.viewmodel.FirestoreDatabaseViewModel
import com.example.newmainproject.ui.viewmodel.UserSingletonViewModel
import java.time.LocalDate


class CreateGroupFragment : Fragment(R.layout.fragment_create_group) {
    private lateinit var binding: FragmentCreateGroupBinding
    private var singletonViewModel: UserSingletonViewModel = UserSingletonViewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateGroupBinding.bind(view)
        val dbViewModel = FirestoreDatabaseViewModel()
        var name: String = ""
        val date = LocalDate.now()
        dbViewModel.Success.observe(viewLifecycleOwner) {
            success -> if (success) {
            Log.d("nazwa", "onViewCreated: $name")
                dbViewModel.updateUserInfo(haveGroup = true, group = name)
                val user = singletonViewModel.getUser()
                user!!.haveGroup = true
                user.group = name
                singletonViewModel.updateUser(user)
            Navigation.findNavController(view).navigate(R.id.action_createGroupFragment_to_groupFragment2)
          }
        }

        binding.buttonCreateGroup.setOnClickListener {
            name = binding.etNameGroup.text.toString()
            val password = binding.etPasswordGroup.text.toString()
            if (name.isNotEmpty() && password.isNotEmpty()) {
                val group = Group(name,password)
                dbViewModel.registerGroup(group)
            }
        }

    }
}