package com.example.newmainproject.ui.fragments

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newmainproject.R
import com.example.newmainproject.adapters.ChoseExerciseAdapter
import com.example.newmainproject.adapters.CreateExerciseAdapter
import com.example.newmainproject.adapters.ProfilePicturesAdapter
import com.example.newmainproject.data.room.ExerciseListDao
import com.example.newmainproject.data.room.ExerciseDatabase
import com.example.newmainproject.data.room.ExerciseRepository
import com.example.newmainproject.databinding.FragmentCreateExerciseBinding
import com.example.newmainproject.models.Exercise
import com.example.newmainproject.models.ExerciseList
import com.example.newmainproject.ui.viewmodel.ExerciseViewModel

import com.example.newmainproject.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetDialog


class CreateExerciseFragment : Fragment(R.layout.fragment_create_exercise), CreateExerciseAdapter.OnItemClickListener,ChoseExerciseAdapter.OnItemClickListener, CreateExerciseAdapter.OnQuantityChangeListener {
    private lateinit var binding: FragmentCreateExerciseBinding
    private lateinit var exerciseList: ArrayList<Exercise>
    private var selectedExerciseIndex: Int = 0
    private var cheat: Int = 0
    private lateinit var rvCreate: RecyclerView
    private lateinit var createExerciseAdapter: CreateExerciseAdapter

    private var savedExerciseList = ArrayList<Exercise>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateExerciseBinding.bind(view)
        val exerciseViewModel = ViewModelProvider(this)[ExerciseViewModel::class.java]

        exerciseList = ArrayList<Exercise>()
        val exercise = Exercise(0,"PUSH UP",0,0,0,R.drawable.firstexercise,0,R.raw.onehandpushup)
        exerciseList.add(exercise)

        rvCreate = binding.rcCreateWorkout
        createExerciseAdapter = CreateExerciseAdapter(exerciseList,this, this)

        rvCreate.adapter = createExerciseAdapter
        rvCreate.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvCreate.setHasFixedSize(false)

        binding.buttonCreateWorkout.setOnClickListener {
            var gif = R.drawable.firstexercise
            if (cheat == 0) {
                 gif = R.drawable.firstexercise
            }
            if (cheat == 1) {
                 gif = R.drawable.secondexercise
            }
            if (cheat == 2) {
                 gif = R.drawable.thirdexercise
            }
            if (cheat == 3) {
                gif = R.drawable.fourthexercise
            }
            cheat++
            val exercise = Exercise(0,"PUSH UP",0,0,0,gif,0,R.raw.onehandpushup)
            exerciseList.add(exercise)
            createExerciseAdapter.notifyDataSetChanged()
        }
        binding.buttonSaveWorkout.setOnClickListener {
            for (i in exerciseList) {
                Toast.makeText(context, "${i.image}/${i.quantity}/${i.breakTime}", Toast.LENGTH_SHORT).show()
            }
            Navigation.findNavController(view).navigate(R.id.action_createExerciseFragment_to_exercisesFragment2)
            val exerciseListSave = ExerciseList(name = binding.exerciseNameEt.text.toString(), exerciseList = exerciseList, date = "")
            exerciseViewModel.insert(exerciseListSave)

        }


    }

    override fun onItemClick(position: Int) {
        Toast.makeText(context, "$position", Toast.LENGTH_SHORT).show()
        selectedExerciseIndex = position
        showBottomSheet()
    }

    override fun onItemClickTwo(position: Int) {
        exerciseList[selectedExerciseIndex].image = Constants.blankExerciseList()[position].image
        createExerciseAdapter.notifyDataSetChanged()
        Toast.makeText(context, "tescik + $position", Toast.LENGTH_SHORT).show()
    }
    private fun showBottomSheet() {
        val dialogView = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
        val dialog = layoutInflater.inflate(R.layout.bottom_sheet_chose_exercise, null)
        dialogView.setContentView(dialog)

        var rvChoseExercise = dialogView.findViewById<RecyclerView>(R.id.rc_exercise_select)!!
        var profileAdapter = ChoseExerciseAdapter(Constants.blankExerciseList(),this)
        rvChoseExercise.adapter = profileAdapter
        rvChoseExercise.setHasFixedSize(true)
        rvChoseExercise.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        dialogView.show()
    }


    override fun onQuantityChanged(position: Int, quantity: Int) {

    }
}
