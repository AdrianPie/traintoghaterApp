package com.example.newmainproject.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TimePicker
import android.widget.Toast
import androidx.constraintlayout.widget.Group
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newmainproject.R
import com.example.newmainproject.adapters.ChoseExerciseAdapter
import com.example.newmainproject.adapters.CreateExerciseAdapter
import com.example.newmainproject.adapters.GroupMembersAdapter
import com.example.newmainproject.adapters.ProfilePicturesAdapter
import com.example.newmainproject.adapters.ShowExercisesAdapter
import com.example.newmainproject.adapters.ShowExercisesDateAdapter
import com.example.newmainproject.databinding.FragmentGroupBinding
import com.example.newmainproject.models.ExerciseList
import com.example.newmainproject.models.User
import com.example.newmainproject.ui.viewmodel.ExerciseViewModel
import com.example.newmainproject.ui.viewmodel.FirestoreDatabaseViewModel
import com.example.newmainproject.ui.viewmodel.GroupSingletonViewModel
import com.example.newmainproject.ui.viewmodel.ImagesViewModel
import com.example.newmainproject.ui.viewmodel.UserSingletonViewModel
import com.example.newmainproject.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class GroupFragment : Fragment(R.layout.fragment_group), ShowExercisesDateAdapter.OnItemClickListener {
    private lateinit var binding: FragmentGroupBinding
    private lateinit var navController: NavController
    private lateinit var membersAdapter: GroupMembersAdapter
    private lateinit var recyclerViewExercises: RecyclerView
    private lateinit var confirmButton: Button
    private lateinit var userList: ArrayList<User>
    private var selecterExercise: Int = 0
    private lateinit var exerciseList: List<ExerciseList>
    private var userSingletonViewModel = UserSingletonViewModel()
    private val groupSingletonViewModel by viewModels<GroupSingletonViewModel>()
    private var firestoreViewModel = FirestoreDatabaseViewModel()
    private val room by viewModels<ExerciseViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroupBinding.bind(view)
        navController = Navigation.findNavController(view)
        val user = userSingletonViewModel.getUser()
        val rv = binding.groupMembersRecyclerview
        val setExerciseDateButton =  binding.setExerciseDate
        binding.groupnameGroup.text = user!!.group

        room.getAllExercises().observe(viewLifecycleOwner) {
            list -> exerciseList = list
        }

        if (user.haveGroup) {
            rv.visibility = View.VISIBLE
            setExerciseDateButton .visibility = View.VISIBLE
            binding.groupInfo.visibility = View.VISIBLE
            binding.createGroupCv.visibility = View.GONE
            binding.joinGroupCv.visibility = View.GONE
            Log.d("test1", "onViewCreated: test1${user.group}")
            firestoreViewModel.getGroup(user.group)
            Log.d("test1", "onViewCreated: test2")
        } else {
            setExerciseDateButton .visibility = View.GONE
            binding.groupMembersRecyclerview.visibility = View.GONE
            binding.groupInfo.visibility = View.GONE
            binding.createGroupCv.visibility = View.VISIBLE
            binding.joinGroupCv.visibility = View.VISIBLE
        }
        setExerciseDateButton.setOnClickListener {
            showBottomSheet()
        }

        firestoreViewModel.SuccessGetGroup.observe(viewLifecycleOwner) {
            group ->
            groupSingletonViewModel.fetchGroup(group)
            firestoreViewModel.getAllMembers(group.members)
            binding.groupMembersLiczba.text = "${group.members.size.toString()} members"
        }
        firestoreViewModel.SuccessGetAllMembers.observe(viewLifecycleOwner) {
            userList ->
            membersAdapter = GroupMembersAdapter(userList)
            rv.adapter = membersAdapter
            rv.setHasFixedSize(true)
            rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }



        binding.joinGroupCv.setOnClickListener {
            navController.navigate(R.id.action_groupFragment2_to_joinGroupFragment2)
        }

        binding.createGroupCv.setOnClickListener {
            navController.navigate(R.id.action_groupFragment2_to_createGroupFragment)
        }


    }
    private fun showBottomSheet() {
        val dialogView = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
        val dialog = layoutInflater.inflate(R.layout.bottom_sheet_chose_exercise_date, null)
        dialogView.setContentView(dialog)
        confirmButton = dialogView.findViewById<Button>(R.id.confirmButton)!!
        val timepicker = dialogView.findViewById<TimePicker>(R.id.timePicker)
        val date = dialogView.findViewById<CalendarView>(R.id.calendarView)
        var dateString = ""
        date!!.setOnDateChangeListener {
            view,year,month,dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            dateString = dateFormat.format(selectedDate.time)
        }
        confirmButton.setOnClickListener {
            val hour = timepicker!!.hour
            val minute = timepicker.minute
            val timeString = String.format("%02d:%02d", hour, minute) // Format the time as HH:mm
            val finalDateTimeString = "$timeString - $dateString" // Combine the time and date strings
            Toast.makeText(requireContext(), "dzialas? $finalDateTimeString", Toast.LENGTH_SHORT).show()
            // Store the finalDateTimeString value to your ExerciseList object or do whatever you need with it
            val exercise = exerciseList[selecterExercise]
            exercise.date = finalDateTimeString
            firestoreViewModel.updateGroup(nextExercise = exercise, name = groupSingletonViewModel.getGroup()!!.name)
        }
        recyclerViewExercises = dialogView.findViewById(R.id.recyclerView_exercises_select)!!
        val profileAdapter = ShowExercisesDateAdapter(exerciseList,this)
        recyclerViewExercises.adapter = profileAdapter
        recyclerViewExercises.setHasFixedSize(true)
        recyclerViewExercises.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        dialogView.show()
    }

    override fun onItemClick(position: Int) {
        selecterExercise = position
        Toast.makeText(requireContext(), "dziala + $position", Toast.LENGTH_SHORT).show()
    }


}