package com.lumi.submission2.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "users_favorite")
@Parcelize
data class UserEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "avatar_url")
    var avatar: String? = null,

    @ColumnInfo(name = "login")
    var username: String? = null,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "company")
    val company: String? = null,

    @ColumnInfo(name = "location")
    val location: String? = null,

    @ColumnInfo(name = "followers")
    val follower: Int? = null,

    @ColumnInfo(name = "following")
    val following: Int? = null
        ):Parcelable