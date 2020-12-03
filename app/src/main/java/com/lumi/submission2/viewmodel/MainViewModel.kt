package com.lumi.submission2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.lumi.submission2.BuildConfig
import com.lumi.submission2.db.UserEntity
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel:ViewModel() {
    private val listUsers = MutableLiveData<ArrayList<UserEntity>>()

    fun setUser(username:String) {
        val listUser = ArrayList<UserEntity>()

        val apiKey:String = BuildConfig.API_KEY
        val url = "https://api.github.com/search/users?q=$username"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {

                val result = String(responseBody)
                Log.d("Exception", result)

                try {
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")

                    for (i in 0 until items.length()){
                        val item = items.getJSONObject(i)
                        val user = UserEntity()
                        user.username = item.getString("login")
                        user.avatar = item.getString("avatar_url")
                        listUser.add(user)
                    }

                    listUsers.postValue(listUser)

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getUsers(): LiveData<ArrayList<UserEntity>> {
        return listUsers
    }
}