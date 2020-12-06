package com.lumi.consumerapp.db

import android.net.Uri

class ContentUri {

    companion object {
        private const val AUTHORITY = "com.lumi.submission2"
        private const val SCHEME = "content"
        private const val TABLE_NAME = "users_favorite"

        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
    }
}