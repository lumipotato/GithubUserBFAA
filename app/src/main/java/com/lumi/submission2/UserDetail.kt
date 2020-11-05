package com.lumi.submission2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDetail(
    var id: Int = 0,
    var avatar: String? = null,
    var username: String? = null,
    var name: String? = null,
    var company: String? = null,
    var location: String? = null,
    var follower: Int = 0,
    var following: Int = 0

) : Parcelable