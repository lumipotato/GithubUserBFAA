package com.lumi.consumerapp.viewmodel

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.lumi.consumerapp.BuildConfig
import com.lumi.consumerapp.db.ContentUri.Companion.CONTENT_URI
import com.lumi.consumerapp.db.UserEntity
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel:ViewModel() {
    private lateinit var uriWithId: Uri
    private val detailUsers = MutableLiveData<UserEntity>()
    private val favoriteUser = MutableLiveData<Cursor>()

    fun setDetail(username: String) {

        val apiKey:String = BuildConfig.API_KEY
        val url = "https://api.github.com/users/$username"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {

                val result = String(responseBody)
                Log.d("Exception", result)

                try {
                    val responseObject = JSONObject(result)

                    val user = UserEntity()
                    user.id = responseObject.getInt("id")
                    user.username = responseObject.getString("login")
                    user.avatar = responseObject.getString("avatar_url")
                    user.name = responseObject.getString("name")
                    user.company = responseObject.getString("company")
                    user.location = responseObject.getString("location")
                    user.follower = responseObject.getInt("followers")
                    user.following = responseObject.getInt("following")

                    detailUsers.postValue(user)

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun setFavoriteUser(users_favorite: ContentValues, context: Context) {
        context.contentResolver.insert(CONTENT_URI, users_favorite)
        Log.d("setFavoriteUser", "display : $CONTENT_URI")
    }

    fun deleteFavoriteUser(id: Int, context: Context) {
        uriWithId = Uri.parse("$CONTENT_URI/$id")
        context.contentResolver.delete(uriWithId, null, null)
        Log.d("deleteFavoriteUser", "display : $uriWithId")
    }

    fun setFavoriteById(id: Int, context: Context) {
        uriWithId = Uri.parse("$CONTENT_URI/$id")
        val cursor = context.contentResolver.query(uriWithId,null, null,null,null)
        favoriteUser.postValue(cursor)
        Log.d("setFavoriteById", "display : $uriWithId")
    }

    fun getFavoriteById(): LiveData<Cursor> {
        return favoriteUser
    }

    fun getDetails(): LiveData<UserEntity> {
        return detailUsers
    }

}