package com.example.githubuser.repository

import androidx.annotation.WorkerThread
import com.example.githubuser.db.FavoriteUserDao
import com.example.githubuser.entity.User

class UserRepository(private val userDao: FavoriteUserDao) {

    fun getFavoriteUser(username: String) = userDao.getFavoriteUser(username)

    @WorkerThread
    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    @WorkerThread
    suspend fun delete(username: String) {
        userDao.deleteByUsername(username)
    }
}