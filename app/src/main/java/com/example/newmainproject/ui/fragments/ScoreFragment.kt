package com.example.newmainproject.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.newmainproject.R
import com.example.newmainproject.databinding.FragmentScoreBinding
import com.example.newmainproject.models.ExerciseList
import com.example.newmainproject.ui.viewmodel.FirestoreDatabaseViewModel
import com.example.newmainproject.ui.viewmodel.GoldScoreViewModel
import com.example.newmainproject.ui.viewmodel.GroupSingletonViewModel


class ScoreFragment : Fragment(R.layout.fragment_score) {
    private lateinit var binding: FragmentScoreBinding
    private val goldScoreViewModel by viewModels<GoldScoreViewModel>()
    private val dbViewModel by viewModels<FirestoreDatabaseViewModel>()
    private val groupSingletonViewModel by viewModels<GroupSingletonViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentScoreBinding.bind(view)
        var isButtonClicked = false
        var claimButton = binding.buttonClaimReward
        val nextExercise = ExerciseList()
        dbViewModel.updateGroup(name = groupSingletonViewModel.getGroup()!!.name, nextExercise = nextExercise)

        claimButton.setOnClickListener {

            if (!isButtonClicked) {
                goldScoreViewModel.updateGoldScore(15)
                claimButton.isEnabled = false
                isButtonClicked = true
            }
            Navigation.findNavController(view).navigate(R.id.action_scoreFragment_to_homeFragment)

        }
    }


}