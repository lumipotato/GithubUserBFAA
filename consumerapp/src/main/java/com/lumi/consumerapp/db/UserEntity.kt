package com.lumi.consumerapp.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "users_favorite")
@Parcelize
data class UserEntity (
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "avatar_url")
    var avatar: String? = null,

    @ColumnInfo(name = "login")
    var username: String? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "company")
    var company: String? = null,

    @ColumnInfo(name = "location")
    var location: String? = null,

    @ColumnInfo(name = "followers")
    var follower: Int? = null,

    @ColumnInfo(name = "following")
    var following: Int? = null
        ):Parcelable