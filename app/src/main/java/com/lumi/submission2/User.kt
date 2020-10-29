package com.lumi.submission2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: Int = 0,
    var avatar: String? = null,
    var username: String? = null,
) : Parcelable


