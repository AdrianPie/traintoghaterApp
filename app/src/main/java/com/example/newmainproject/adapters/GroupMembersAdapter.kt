package com.example.newmainproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newmainproject.R
import com.example.newmainproject.models.User

class GroupMembersAdapter(
    private val memberList: ArrayList<User>,
) : RecyclerView.Adapter<GroupMembersAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupMembersAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.group_member_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = memberList[position]
        holder.image.setImageResource(item.image)
        holder.name.text = item.name


    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var image: ImageView = itemView.findViewById(R.id.profile_picture_member)
        var name: TextView = itemView.findViewById(R.id.name_member)
        var stats: TextView = itemView.findViewById(R.id.stats_member)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
            }

        }
    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}