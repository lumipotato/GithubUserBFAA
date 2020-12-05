package com.lumi.submission2.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lumi.submission2.db.MappingHelper
import com.lumi.submission2.db.UserEntity
import com.lumi.submission2.db.UserProvider.Companion.CONTENT_URI

class FavoriteViewModel:ViewModel() {

    private val favoriteListUser = MutableLiveData<ArrayList<UserEntity>>()

    fun setFavoriteListUser(context: Context) {
        val cursor = context.contentResolver.query(CONTENT_URI,null, null,null,null)
        val favoriteListData = MappingHelper.mapCursorToArrayLits(cursor)
        favoriteListUser.postValue(favoriteListData)
        Log.d("setFavoriteListUser", "display : $CONTENT_URI")
    }

    fun getListFavorite(): LiveData<ArrayList<UserEntity>> {
        return favoriteListUser
    }
}