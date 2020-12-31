package com.example.githubuser.db

import android.net.Uri

object DatabaseContract {

    const val AUTHORITY = "com.example.githubuser"
    const val SCHEME = "content"
    const val TABLE_NAME = "favorite_user"

    class FavUserColumns {
        companion object {
            const val USERNAME = "username"
            const val AVATAR = "avatar"
            const val NAME = "name"
            const val COMPANY = "company"
            const val REPOSITORY = "repository"
            const val LOCATION = "location"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()

            fun getColumnNames() = arrayOf(USERNAME, AVATAR, NAME, COMPANY, REPOSITORY, LOCATION, FOLLOWERS, FOLLOWING)
        }
    }
}