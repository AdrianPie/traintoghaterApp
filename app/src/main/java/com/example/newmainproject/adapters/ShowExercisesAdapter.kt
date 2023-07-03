package com.example.newmainproject.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.newmainproject.R
import com.example.newmainproject.models.Exercise
import com.example.newmainproject.models.ExerciseList


class ShowExercisesAdapter(
    private val exerciseList: List<ExerciseList>,
    ) :
    RecyclerView.Adapter<ShowExercisesAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowExercisesAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exercises_done_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = exerciseList[position]
        holder.name.text = item.name


    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),
    OnClickListener{
        val name: TextView = itemView.findViewById(R.id.name_exercise_row)
        val stats: TextView = itemView.findViewById(R.id.stats_exercise_row)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition

        }
    }

}