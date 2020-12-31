package com.example.githubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.githubuser.db.DatabaseContract.AUTHORITY
import com.example.githubuser.db.DatabaseContract.TABLE_NAME
import com.example.githubuser.db.FavoriteUserDao
import com.example.githubuser.db.FavoriteUserRoomDatabase

class FavUserProvider : ContentProvider() {

    private lateinit var roomDao: FavoriteUserDao

    companion object {
        private const val USERS = 1
        private const val USER_USERNAME = 2
        private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)
        init {
            MATCHER.addURI(AUTHORITY, TABLE_NAME, USERS)
            MATCHER.addURI(AUTHORITY, "$TABLE_NAME/#", USER_USERNAME)
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("The operation is not supported in this app")
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException("The operation is not supported in this app")
    }

    override fun onCreate(): Boolean {
        context?.let {
            roomDao = FavoriteUserRoomDatabase.getDatabase(it).favUserDao()
        }
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val code = MATCHER.match(uri)
        if(code != USERS) return null
        val context = context ?: return null
        val cursor = roomDao.getAllFavoriteUser()
        cursor.setNotificationUri(context.contentResolver, uri)
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw UnsupportedOperationException("The operation is not supported in this app")
    }
}
