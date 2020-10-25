package com.example.githubuser

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User : Parcelable {
    var username: String? = null
    var name: String? = null
    var company: String? = null
    var repository: String? = null
    var location: String? = null
    var avatar: String? = null
}