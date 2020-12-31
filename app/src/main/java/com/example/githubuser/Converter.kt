package com.example.githubuser

import android.database.Cursor
import androidx.room.TypeConverter
import com.example.githubuser.db.DatabaseContract.FavUserColumns.Companion.AVATAR
import com.example.githubuser.db.DatabaseContract.FavUserColumns.Companion.COMPANY
import com.example.githubuser.db.DatabaseContract.FavUserColumns.Companion.FOLLOWERS
import com.example.githubuser.db.DatabaseContract.FavUserColumns.Companion.FOLLOWING
import com.example.githubuser.db.DatabaseContract.FavUserColumns.Companion.LOCATION
import com.example.githubuser.db.DatabaseContract.FavUserColumns.Companion.NAME
import com.example.githubuser.db.DatabaseContract.FavUserColumns.Companion.REPOSITORY
import com.example.githubuser.db.DatabaseContract.FavUserColumns.Companion.USERNAME
import com.example.githubuser.entity.User
import com.google.gson.Gson

object Converters {
    @TypeConverter
    fun fromArrayList(list: ArrayList<User>): String = Gson().toJson(list)

    fun fromCursorToObject(cursor: Cursor) : User {
        lateinit var user: User
        cursor.apply {
            val username = getString(getColumnIndexOrThrow(USERNAME))
            val avatar = getString(getColumnIndexOrThrow(AVATAR))
            val name = getString(getColumnIndexOrThrow(NAME))
            val company = getString(getColumnIndexOrThrow(COMPANY))
            val repository = getString(getColumnIndexOrThrow(REPOSITORY))
            val location = getString(getColumnIndexOrThrow(LOCATION))
            val followers = getString(getColumnIndexOrThrow(FOLLOWERS))
            val following = getString(getColumnIndexOrThrow(FOLLOWING))
            user = User(username, avatar, name, company, repository, location, followers, following)
        }
        return user
    }

    fun fromCursorToList(cursor: Cursor) : ArrayList<User> {
        val users = ArrayList<User>()
        cursor.apply {
            while(moveToNext()) {
                val username = getString(getColumnIndexOrThrow(USERNAME))
                val avatar = getString(getColumnIndexOrThrow(AVATAR))
                val name = getString(getColumnIndexOrThrow(NAME))
                val company = getString(getColumnIndexOrThrow(COMPANY))
                val repository = getString(getColumnIndexOrThrow(REPOSITORY))
                val location = getString(getColumnIndexOrThrow(LOCATION))
                val followers = getString(getColumnIndexOrThrow(FOLLOWERS))
                val following = getString(getColumnIndexOrThrow(FOLLOWING))
                users.add(User(username, avatar, name, company, repository, location, followers, following))
            }
        }
        return users
    }
}