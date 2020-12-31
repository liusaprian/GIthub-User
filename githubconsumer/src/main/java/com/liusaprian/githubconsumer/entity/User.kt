package com.liusaprian.githubconsumer.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var username: String,
    var avatar: String? = null,
    var name: String? = null,
    var company: String? = null,
    var repository: String? = null,
    var location: String? = null,
    var followers: String? = null,
    var following: String? = null
) : Parcelable