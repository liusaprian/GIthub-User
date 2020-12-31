package com.example.githubuser

import android.app.Application
import com.example.githubuser.db.FavoriteUserRoomDatabase
import com.example.githubuser.repository.UserRepository

class FavUserApplication : Application() {
    private val database by lazy { FavoriteUserRoomDatabase.getDatabase(this) }
    val repository by lazy { UserRepository(database.favUserDao()) }
}