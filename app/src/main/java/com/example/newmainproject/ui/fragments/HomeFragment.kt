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
import com.example.newmainproject.utils.TimerHome
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
    private lateinit var timer: TimerHome



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        var readyListTwo = ArrayList<Boolean>()
        var users = ArrayList<User>()
        val rv = binding.rvMemberCheckList
        val group = groupSingletonViewModel.getGroup()
        if (group!= null) {
            firestoreDatabaseViewModel.addGroupSnapshotListener(group.name) {
                    updatedGroup ->
            }
        }


        firestoreDatabaseViewModel.SuccessGetListenerGroup.observe(viewLifecycleOwner) { group ->
            groupSingletonViewModel.updateGroup(group)
            readyListTwo = group.readyListL


            if (::adapterx.isInitialized) {
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

                val delayInMillis = 1000L
                val currentTime = Date()
                val timeDifferenceMillis = targetDate.time - currentTime.time
                timer = TimerHome(timeDifferenceMillis, delayInMillis)
                setTimerListener(timer)
                timer.startTimer()

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
 private fun setTimerListener(timer: TimerHome){
     timer.setTimerListener(object : TimerHome.TimerListener {
         override fun onTimerTick(remainingTime: Long) {
             val hours = TimeUnit.MILLISECONDS.toHours(remainingTime)
             val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime) % 60
             val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTime) % 60

             val Time = String.format("%02d:%02d:%02d", hours, minutes, seconds)
             binding.timeToNextSession.text = Time
             if (remainingTime < 300000) {
                 binding.buttonJoin.visibility = View.VISIBLE
             }
         }
         override fun onTimerFinish() {
             val group = groupSingletonViewModel.getGroup()
             val user = userSingletonViewModel.getUser()
             firestoreDatabaseViewModel.updateUserInfo(presence =  (user!!.presence + 1))
             Log.d("grupa", "onTimerFinish: ${group!!.presence}  ${group.name}")
             firestoreDatabaseViewModel.updateGroup(name = group!!.name, presence = (group.presence + 1))
             Navigation.findNavController(view!!).navigate(R.id.action_homeFragment_to_exerciseFragment)
         }
     })
 }

}