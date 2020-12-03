 package com.lumi.submission2.db

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class UserProvider : ContentProvider() {

    companion object {

        private const val AUTHORITY = "com.lumi.submission2"
        private const val SCHEME = "content"
        private const val TABLE_NAME = "users_favorite"
        private const val FAVORITE = 1
        private const val FAVORITE_ID = 2

        private lateinit var appDatabase: AppDatabase
        private lateinit var userDao: UserDao

        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE)

            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", FAVORITE_ID)
        }
    }

    override fun onCreate(): Boolean {
        appDatabase = AppDatabase.getDatabase(context as Context)
        appDatabase.userDao()
        return true
    }

    override fun getType(uri: Uri): String? {
       return null
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            FAVORITE -> userDao.getUsers()
            FAVORITE_ID -> userDao.getUserById(uri.lastPathSegment!!.toInt())
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val addId: Long = when (FAVORITE) {
            sUriMatcher.match(uri) -> userDao.insertUser(values.let {
                MappingHelper.convertFromContentValues(
                    it!!
                )
            })
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return Uri.parse("$CONTENT_URI/$addId")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleteId: Int = when (FAVORITE_ID) {
            sUriMatcher.match(uri) -> userDao.deleteUser(uri.lastPathSegment!!.toInt())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return deleteId
    }
}