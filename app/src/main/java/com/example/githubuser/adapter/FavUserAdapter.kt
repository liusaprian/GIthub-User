package com.example.githubuser.adapter

import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.Converters
import com.example.githubuser.db.DatabaseContract.FavUserColumns.Companion.AVATAR
import com.example.githubuser.db.DatabaseContract.FavUserColumns.Companion.USERNAME
import com.example.githubuser.R
import com.example.githubuser.view.detail.DetailActivity

class FavUserAdapter : RecyclerView.Adapter<FavUserAdapter.FavUserViewHolder>() {

    private var cursor: Cursor? = null

    fun setCursor(cursor: Cursor?) {
        this.cursor = cursor
        notifyDataSetChanged()
    }

    class FavUserViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_user, parent, false)) {
        val username: TextView = itemView.findViewById(R.id.username)
        val avatar: ImageView = itemView.findViewById(R.id.avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavUserViewHolder(parent)

    override fun onBindViewHolder(holder: FavUserViewHolder, position: Int) {
        if(cursor!!.moveToPosition(position)) {
            val favUser = Converters.fromCursorToObject(cursor!!)

            holder.username.text = favUser.username
            Glide.with(holder.itemView.context)
                .load(favUser.avatar)
                .into(holder.avatar)

            holder.itemView.setOnClickListener {
                val toDetailActivity = Intent(holder.itemView.context, DetailActivity::class.java)
                toDetailActivity.putExtra(USERNAME, favUser.username)
                toDetailActivity.putExtra(AVATAR, favUser.avatar)
                holder.itemView.context.startActivity(toDetailActivity)
            }
        }
    }

    override fun getItemCount(): Int {
        return if(cursor == null) 0
        else cursor!!.count
    }
}