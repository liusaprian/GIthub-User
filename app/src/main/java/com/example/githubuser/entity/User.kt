package com.example.githubuser.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favorite_user")
@Parcelize
data class User (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "avatar")
    var avatar: String? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "company")
    var company: String? = null,

    @ColumnInfo(name = "repository")
    var repository: String? = null,

    @ColumnInfo(name = "location")
    var location: String? = null,

    @ColumnInfo(name = "followers")
    var followers: String? = null,

    @ColumnInfo(name = "following")
    var following: String? = null
) : Parcelable