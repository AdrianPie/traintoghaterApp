package com.example.newmainproject.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newmainproject.R
import com.example.newmainproject.adapters.ShowExercisesAdapter
import com.example.newmainproject.data.singleton.UserSingleton
import com.example.newmainproject.databinding.FragmentExercisesBinding
import com.example.newmainproject.models.Exercise
import com.example.newmainproject.models.ExerciseList
import com.example.newmainproject.ui.viewmodel.ExerciseViewModel
import com.example.newmainproject.ui.viewmodel.UserSingletonViewModel
import com.google.firebase.auth.FirebaseAuth


class ExercisesFragment : Fragment(R.layout.fragment_exercises) {

    private lateinit var binding: FragmentExercisesBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val exerciseViewModel: ExerciseViewModel by viewModels()
    lateinit var exerciseList: List<ExerciseList>
    private var userViewModel: UserSingletonViewModel = UserSingletonViewModel()
    private lateinit var test: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExercisesBinding.bind(view)
        val rv = binding.rvWorkoutList
         exerciseViewModel.getAllExercises().observe(viewLifecycleOwner) {
             list -> exerciseList = list

                 val adapter = ShowExercisesAdapter(exerciseList)
                 rv.adapter = adapter
                 rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

         }





        binding.createWorkout.setOnClickListener {
          Navigation.findNavController(view).navigate(R.id.action_exercisesFragment2_to_createExerciseFragment)
        }

    }



}