package com.example.newmainproject.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.newmainproject.R
import com.example.newmainproject.databinding.FragmentJoinGroupBinding
import com.example.newmainproject.ui.viewmodel.FirestoreDatabaseViewModel
import com.example.newmainproject.ui.viewmodel.UserSingletonViewModel


class JoinGroupFragment : Fragment(R.layout.fragment_join_group) {
    lateinit var binding: FragmentJoinGroupBinding
    private var singletonViewModel: UserSingletonViewModel = UserSingletonViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentJoinGroupBinding.bind(view)
        var name: String = ""
        val dbViewModel = FirestoreDatabaseViewModel()

        dbViewModel.Success.observe(viewLifecycleOwner){
            success ->  if (success) {
            dbViewModel.updateUserInfo(haveGroup = true, group = name)
            val user = singletonViewModel.getUser()
            user!!.haveGroup = true
            user.group = name
            singletonViewModel.updateUser(user)
            Toast.makeText(context, "GJ DOSZEDLES DO GRUPY", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(view).navigate(R.id.action_joinGroupFragment2_to_groupFragment2)
        }
        }

        binding.buttonCreateGroup.setOnClickListener {
            name = binding.etNameGroupJoin.text.toString()
            val password = binding.etPasswordGroupJoin.text.toString()

            dbViewModel.registerUserToGroup(name,password)


        }
    }


}