package com.example.newmainproject.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.newmainproject.R
import com.example.newmainproject.models.Exercise
import com.example.newmainproject.models.ExerciseList


class ShowExercisesDateAdapter(
    private val exerciseList: List<ExerciseList>,
    private val listener: OnItemClickListener
    ):
    RecyclerView.Adapter<ShowExercisesDateAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowExercisesDateAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exercises_done_row, parent, false)
        return ViewHolder(view)
    }
    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = exerciseList[position]
        holder.name.text = item.name
        if (selectedPosition == position) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.selected_color))
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.default_color))
        }


    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),
    OnClickListener{
        val name: TextView = itemView.findViewById(R.id.name_exercise_row)
        val stats: TextView = itemView.findViewById(R.id.stats_exercise_row)
        val cardView: CardView = itemView.findViewById(R.id.cardview_exercise_done)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                selectedPosition = position
                notifyDataSetChanged()
                listener.onItemClick(position)
            }
        }
    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}