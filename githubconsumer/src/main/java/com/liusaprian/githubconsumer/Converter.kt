package com.liusaprian.githubconsumer

import android.database.Cursor
import androidx.room.TypeConverter
import com.liusaprian.githubconsumer.entity.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.liusaprian.githubconsumer.DatabaseContract.FavUserColumns.Companion.AVATAR
import com.liusaprian.githubconsumer.DatabaseContract.FavUserColumns.Companion.COMPANY
import com.liusaprian.githubconsumer.DatabaseContract.FavUserColumns.Companion.FOLLOWERS
import com.liusaprian.githubconsumer.DatabaseContract.FavUserColumns.Companion.FOLLOWING
import com.liusaprian.githubconsumer.DatabaseContract.FavUserColumns.Companion.LOCATION
import com.liusaprian.githubconsumer.DatabaseContract.FavUserColumns.Companion.NAME
import com.liusaprian.githubconsumer.DatabaseContract.FavUserColumns.Companion.REPOSITORY
import com.liusaprian.githubconsumer.DatabaseContract.FavUserColumns.Companion.USERNAME

object Converters {
    @TypeConverter
    fun fromString(value: String?): ArrayList<User> {
        val listType = object : TypeToken<ArrayList<User>>() {}.type
        return Gson().fromJson(value, listType)
    }

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
}