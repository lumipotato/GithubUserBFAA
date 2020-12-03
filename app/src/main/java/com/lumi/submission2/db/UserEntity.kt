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
    val id: Int,

    @ColumnInfo(name = "avatar_url")
    val avatar: String?,

    @ColumnInfo(name = "login")
    val username: String?,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "company")
    val company: String?,

    @ColumnInfo(name = "location")
    val location: String?,

    @ColumnInfo(name = "followers")
    val follower: Int,

    @ColumnInfo(name = "following")
    val following: Int
        ):Parcelable