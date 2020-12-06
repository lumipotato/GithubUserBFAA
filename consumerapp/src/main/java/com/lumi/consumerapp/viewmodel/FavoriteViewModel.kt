package com.lumi.consumerapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lumi.consumerapp.db.ContentUri.Companion.CONTENT_URI
import com.lumi.consumerapp.db.MappingHelper
import com.lumi.consumerapp.db.UserEntity

class FavoriteViewModel:ViewModel() {

    private val favoriteListUser = MutableLiveData<ArrayList<UserEntity>>()

    fun setFavoriteListUser(context: Context) {
        val cursor = context.contentResolver.query(CONTENT_URI,null, null,null,null)
        val favoriteListData = MappingHelper.mapCursorToArrayList(cursor)
        favoriteListUser.postValue(favoriteListData)
        Log.d("setFavoriteListUser", "display : $CONTENT_URI")
    }

    fun getListFavorite(): LiveData<ArrayList<UserEntity>> {
        return favoriteListUser
    }
}