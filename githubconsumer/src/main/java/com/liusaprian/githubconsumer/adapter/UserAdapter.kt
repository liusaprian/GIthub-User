package com.liusaprian.githubconsumer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.liusaprian.githubconsumer.R
import com.liusaprian.githubconsumer.entity.User
import kotlinx.android.synthetic.main.row_user.view.*

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val user = ArrayList<User>()

    fun setUser(user: ArrayList<User>) {
        this.user.clear()
        this.user.addAll(user)
        notifyDataSetChanged()
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .into(avatar)
                username.text = user.username
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_user, parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) = holder.bind(user[position])

    override fun getItemCount() = user.size
}