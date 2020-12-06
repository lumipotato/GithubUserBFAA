package com.lumi.consumerapp.db

import android.content.ContentValues
import android.database.Cursor

class MappingHelper {
    companion object {


        fun convertToContentValues(userEntity: UserEntity): ContentValues {
            val values = ContentValues()

            values.put("id", userEntity.id)
            values.put("avatar_url", userEntity.avatar)
            values.put("login", userEntity.username)
            values.put("name", userEntity.name)
            values.put("company", userEntity.company)
            values.put("location", userEntity.location)
            values.put("followers", userEntity.follower)
            values.put("following", userEntity.following)

            return values
        }

        fun mapCursorToArrayList(favoritesCursor: Cursor?): ArrayList<UserEntity> {
            val favoriteList = ArrayList<UserEntity>()

            favoritesCursor?.apply {
                while (moveToNext()) {
                    val id = getInt(getColumnIndexOrThrow("id"))
                    val avatarUrl = getString(getColumnIndexOrThrow("avatar_url"))
                    val login = getString(getColumnIndexOrThrow("login"))
                    val name = getString(getColumnIndexOrThrow("name"))
                    val company = getString(getColumnIndexOrThrow("company"))
                    val location = getString(getColumnIndexOrThrow("location"))
                    val followers = getInt(getColumnIndexOrThrow("followers"))
                    val following = getInt(getColumnIndexOrThrow("following"))

                    favoriteList.add(
                        UserEntity(
                            id,
                            avatarUrl,
                            login,
                            name,
                            company,
                            location,
                            followers,
                            following
                        )
                    )
                }
            }

            return favoriteList
        }
    }
}