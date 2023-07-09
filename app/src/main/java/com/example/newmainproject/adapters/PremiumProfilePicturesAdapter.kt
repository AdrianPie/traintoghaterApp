package com.example.newmainproject.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.newmainproject.R




class PremiumProfilePicturesAdapter(
    private val list: ArrayList<Int>,
    private val listener: OnItemClickListener,
    private val boughtList: ArrayList<Boolean>
    ) :
    RecyclerView.Adapter<PremiumProfilePicturesAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PremiumProfilePicturesAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.premium_pictures_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        val bought = boughtList[position]
        holder.image.setImageResource(item)

        if (bought) {
            holder.button.text = "BOUGHT"
        }


    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),
    OnClickListener{
        val image: ImageView = itemView.findViewById(R.id.premium_image)
        val button: Button = itemView.findViewById(R.id.button_buy)
        init {
            button.setOnClickListener(this)
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
}