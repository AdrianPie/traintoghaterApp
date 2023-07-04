package com.example.newmainproject.ui.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.newmainproject.R
import com.example.newmainproject.databinding.FragmentExerciseBinding
import com.example.newmainproject.models.Exercise
import com.example.newmainproject.ui.viewmodel.GroupSingletonViewModel
import com.example.newmainproject.utils.TimerHome
import com.owl93.dpb.CircularProgressView



class ExerciseFragment : Fragment(R.layout.fragment_exercise) {


    private lateinit var timer: CountDownTimer
    private lateinit var timer2: CountDownTimer
    private lateinit var exerciseList: ArrayList<Exercise>
    private lateinit var progressBar: CircularProgressView
    private var i = 0
    private val groupSingletonViewModel by viewModels<GroupSingletonViewModel>()
    private var fullExerciseTime = 0
    private lateinit var binding: FragmentExerciseBinding
    private lateinit var navController: NavController


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExerciseBinding.bind(view)
        exerciseList = groupSingletonViewModel.getGroup()!!.nextExercise!!.exerciseList
        progressBar = binding.progressBarExercise




        for (i in exerciseList){
            fullExerciseTime += i.quantity* i.duration
        }
        navController = Navigation.findNavController(view)
    }
    override fun onStart() {
        super.onStart()
        startExercise(exerciseList)
        startFullExerciseTimer(exerciseList)
    }
    private fun startFullExerciseTimer(exerciseList: ArrayList<Exercise>) {
        timer = object : CountDownTimer(fullExerciseTime.toLong()*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                Log.d("tik", "onTick: tick+ $millisUntilFinished")
                binding.timeWholeExerciseTv.text = (millisUntilFinished/1000).toString()
            }
            override fun onFinish() {
                Log.d("TAG", "onFinish: dupa")
            }
        }
        timer.start()
    }


    private fun startExercise(exerciseList: ArrayList<Exercise>) {
        val duration = exerciseList[i].duration
        val reps = exerciseList[i].quantity
        Glide.with(this).load(exerciseList[i].gifUri).into(binding.ivExerciseMainGif)
        binding.tvExerciseNameMain.text = exerciseList[i].name
        binding.repsExerciseMainTv.text = "Reps: ${exerciseList[i].quantity}"
        val fullTime = duration*reps
        progressBar.maxValue = fullTime.toFloat()
        setTimerAndProgressBar(fullTime)

    }

    private fun setTimerAndProgressBar(duration: Int) {
        val durationLong = duration.toLong()

        timer2 = object : CountDownTimer(durationLong*1000,1_000){
            override fun onTick(millisUntilFinished: Long) {
                progressBar.progress = (millisUntilFinished/1000).toFloat()
                progressBar.text = (millisUntilFinished/1000).toString()
            }
            override fun onFinish() {
                timer2.cancel()
                i++
                if (i<exerciseList.size) {
                    startExercise(exerciseList)
                } else {
                    Log.d("martynadziwka", "onViewCreated: testy")
                    navController.navigate(R.id.action_exerciseFragment_to_scoreFragment)
                }

            }
        }
        timer2.start()
    }

}