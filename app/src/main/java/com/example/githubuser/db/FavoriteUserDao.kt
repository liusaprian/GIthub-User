package com.example.githubuser.db

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubuser.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteUserDao {

    @Query("SELECT * FROM favorite_user")
    fun getAllFavoriteUser(): Cursor

    @Query("SELECT * FROM favorite_user WHERE username = :username")
    fun getFavoriteUser(username: String): Flow<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favUser: User)

    @Query("DELETE FROM favorite_user WHERE username = :username")
    suspend fun deleteByUsername(username: String)
}