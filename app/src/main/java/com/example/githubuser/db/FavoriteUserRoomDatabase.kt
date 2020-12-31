package com.example.githubuser.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.githubuser.Converters
import com.example.githubuser.entity.User

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FavoriteUserRoomDatabase : RoomDatabase() {

    abstract fun favUserDao(): FavoriteUserDao

    companion object {

        @Volatile
        private var INSTANCE: FavoriteUserRoomDatabase? = null

        fun getDatabase(context: Context): FavoriteUserRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteUserRoomDatabase::class.java,
                    "fav_user_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}