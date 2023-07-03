package com.example.newmainproject.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.newmainproject.R




class ProfilePicturesAdapter(
    private val list: List<Int>,
    private val listener: OnItemClickListener
    ) :
    RecyclerView.Adapter<ProfilePicturesAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilePicturesAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pictures_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.image.setImageResource(item)


    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),
    OnClickListener{
        val image: ImageView = itemView.findViewById(R.id.first_image)
        init {
            itemView.setOnClickListener(this)
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