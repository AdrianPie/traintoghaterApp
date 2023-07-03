package com.example.newmainproject.adapters

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newmainproject.R
import com.example.newmainproject.models.Exercise
import com.example.newmainproject.models.User

class CreateExerciseAdapter(
    private val exerciseList: ArrayList<Exercise>,
    private val listener: OnItemClickListener,
    private var quantityChangeListener: OnQuantityChangeListener
) : RecyclerView.Adapter<CreateExerciseAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateExerciseAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.create_workout_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = exerciseList[position]
        holder.imageView.setImageResource(item.image)

    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val imageView: ImageView = itemView.findViewById(R.id.image_create_workout)
        val quantity: EditText = itemView.findViewById(R.id.quantity_create_exercise)
        val duration: EditText = itemView.findViewById(R.id.duration_create_exercise)
        val sets: EditText = itemView.findViewById(R.id.sets_create_exercise)
        val breakEt: EditText = itemView.findViewById(R.id.break_create_exercise)


        private val quantityWatcher = createTextWatcher { position, quantityValue ->
            exerciseList[position].quantity = quantityValue
            //quantityChangeListener?.onQuantityChanged(position, quantityValue)
        }

        private val durationWatcher = createTextWatcher { position, durationValue ->
            exerciseList[position].duration = durationValue
            // Call listener method if needed
        }

        private val setsWatcher = createTextWatcher { position, setsValue ->
            exerciseList[position].sets = setsValue
            // Call listener method if needed
        }

        private val breakWatcher = createTextWatcher { position, breakValue ->
            exerciseList[position].breakTime = breakValue
            // Call listener method if needed
        }
        init {
            itemView.findViewById<ImageView>(R.id.image_create_workout).setOnClickListener(this)
            quantity.addTextChangedListener(quantityWatcher)
            duration.addTextChangedListener(durationWatcher)
            sets.addTextChangedListener(setsWatcher)
            breakEt.addTextChangedListener(breakWatcher)

        }
        private fun createTextWatcher(onValueChanged: (Int, Int) -> Unit): TextWatcher {
            return object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    Log.d("gowno", "onTextChanged: ")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.d("gowno", "onTextChanged: ")
                }

                override fun afterTextChanged(s: Editable?) {
                    val position = adapterPosition
                    val value = s?.toString()?.toIntOrNull() ?: 0
                    if (position != RecyclerView.NO_POSITION) {
                        onValueChanged(position, value)
                    }
                }
            }
        }



        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
               listener.onItemClick(position)
            }

        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    interface OnQuantityChangeListener {
       fun onQuantityChanged(position: Int, quantity: Int)
    }

}