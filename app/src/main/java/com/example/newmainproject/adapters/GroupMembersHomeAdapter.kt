package com.example.newmainproject.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.newmainproject.R
import com.example.newmainproject.models.User
import com.google.firebase.database.collection.LLRBNode

class GroupMembersHomeAdapter(
    private val memberList: ArrayList<User>,
    private val readyList: ArrayList<Boolean>
) : RecyclerView.Adapter<GroupMembersHomeAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupMembersHomeAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_member_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = memberList[position]
        val ready = readyList[position]
        Log.d("prosze", "onBindViewHolder: $ready")
        if (ready) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#00FF00"))
        }

        holder.image.setImageResource(item.image)
        holder.name.text = item.name


    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var cardView: CardView = itemView.findViewById(R.id.cv_member_home)
        var image: ImageView = itemView.findViewById(R.id.profile_picture_member_home)
        var name: TextView = itemView.findViewById(R.id.name_member_home)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
            }

        }
    }

}