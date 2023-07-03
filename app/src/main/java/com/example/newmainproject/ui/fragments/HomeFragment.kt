package com.example.newmainproject.ui.fragments

import android.os.Bundle
import android.os.Handler

import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newmainproject.R
import com.example.newmainproject.adapters.GroupMembersAdapter
import com.example.newmainproject.adapters.GroupMembersHomeAdapter
import com.example.newmainproject.databinding.FragmentHomeBinding
import com.example.newmainproject.models.Group
import com.example.newmainproject.models.User
import com.example.newmainproject.ui.viewmodel.AuthViewModel
import com.example.newmainproject.ui.viewmodel.FirestoreDatabaseViewModel
import com.example.newmainproject.ui.viewmodel.GroupSingletonViewModel
import com.example.newmainproject.ui.viewmodel.UserSingletonViewModel
import com.example.newmainproject.utils.Constants
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var binding: FragmentHomeBinding
    private var userViewModel: UserSingletonViewModel = UserSingletonViewModel()
    private val firestoreDatabaseViewModel by viewModels<FirestoreDatabaseViewModel>()
    private val groupSingletonViewModel by viewModels<GroupSingletonViewModel>()
    private val userSingletonViewModel by viewModels<UserSingletonViewModel>()
    private lateinit var adapterx: GroupMembersHomeAdapter
    private var updateTimerRunnable: Runnable? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        var readyListTwo = ArrayList<Boolean>()
        var users = ArrayList<User>()
        var rv = binding.rvMemberCheckList

        val navController = Navigation.findNavController(requireView())


        firestoreDatabaseViewModel.addGroupSnapshotListener("test69") {
            upadtedGroup ->
            Log.d("callback1", "onViewCreated: ${upadtedGroup.readyListL[0]}")
        }
        firestoreDatabaseViewModel.SuccessGetListenerGroup.observe(viewLifecycleOwner) { group ->
            groupSingletonViewModel.updateGroup(group)
            readyListTwo = group.readyListL
            Log.d("callback2", "onViewCreated: ${readyListTwo[0]}")

            if (::adapterx.isInitialized) {
                Log.d("callback3", "onViewCreated: ${readyListTwo[0]}")
                adapterx = GroupMembersHomeAdapter(users, readyListTwo)
                rv.adapter = adapterx
                rv.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                rv.setHasFixedSize(true)
                adapterx.notifyDataSetChanged()
            }
        }


        binding.buttonJoin.setOnClickListener {
            binding.buttonJoin.text = "WAITING FOR OTHER MEMBERS"
            val readyList = groupSingletonViewModel.getGroup()!!.readyListL
            val ready = groupSingletonViewModel.getGroup()!!.members.indexOf(Constants.CURRENT_USER_ID)
            readyList[ready] = true
            firestoreDatabaseViewModel.updateGroup(
                name = groupSingletonViewModel.getGroup()!!.name,
                readyList = readyList
            )
        }
        firestoreDatabaseViewModel.getUserInfo()
        firestoreDatabaseViewModel.userInfo.observe(viewLifecycleOwner) {
            user -> userViewModel.fetchUser(user)
            userViewModel.updateUser(user)
            if (user.haveGroup) {
                firestoreDatabaseViewModel.getGroup(user.group)
            }
        }
        firestoreDatabaseViewModel.SuccessGetGroup.observe(viewLifecycleOwner) {
            group ->
            if (group.nextExercise != null) {
                val dateString = group.nextExercise!!.date
                val dateFormat = SimpleDateFormat("HH:mm - dd/MM/yyyy", Locale.getDefault())
                val targetDate = dateFormat.parse(dateString)
                binding.nextSessionTv.text = "NEXT SESSION : $dateString"
                groupSingletonViewModel.fetchGroup(group)

                firestoreDatabaseViewModel.getAllMembers(group.members)

                val handler = Handler()
                val delayInMillis = 1000L

                updateTimerRunnable = object : Runnable {
                    override fun run() {
                        val currentTime = Date()
                        val timeDifferenceMillis = targetDate.time - currentTime.time

                        val hours = TimeUnit.MILLISECONDS.toHours(timeDifferenceMillis)
                        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifferenceMillis) % 60
                        val seconds = TimeUnit.MILLISECONDS.toSeconds(timeDifferenceMillis) % 60
                        val secondsToGo = TimeUnit.MILLISECONDS.toSeconds(timeDifferenceMillis)
                        val remainingTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                        binding.timeToNextSession.text = remainingTime
                        if (secondsToGo < 300) {
                            binding.buttonJoin.visibility = View.VISIBLE
                        }
                        if (secondsToGo <= 1) {
                            handler.removeCallbacks(updateTimerRunnable!!)
                            navController.currentDestination?.id?.let { destinationId ->
                                if (destinationId == R.id.homeFragment) {
                                    navController.navigate(R.id.action_homeFragment_to_exerciseFragment)
                                }
                            }

                        }

                        handler.postDelayed(this, delayInMillis)
                    }
                }


                handler.postDelayed(updateTimerRunnable!!, delayInMillis)
            }


        }
        firestoreDatabaseViewModel.SuccessGetAllMembers.observe(viewLifecycleOwner) { usersx ->

            readyListTwo = groupSingletonViewModel.getGroup()!!.readyListL
            users = usersx
            adapterx = GroupMembersHomeAdapter(users, readyListTwo)
            rv.adapter = adapterx
            rv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rv.setHasFixedSize(true)
        }

    }

}