package com.lumi.submission2.db

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(users_favorite: UserEntity): Long

    @Query("SELECT * FROM users_favorite ORDER BY name ASC")
    fun getUsers(): Cursor

    @Query("SELECT * FROM users_favorite WHERE id = :id")
    fun getUserById(id: Int): Cursor

    @Query("DELETE FROM users_favorite WHERE id = :id")
    fun deleteUser(id: Int): Int
}