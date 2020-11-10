package com.lumi.submission2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.lumi.submission2.model.User
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowerViewModel:ViewModel() {
    private val listUsers = MutableLiveData<ArrayList<User>>()

    fun setFollower(username: String) {
        val listUser = ArrayList<User>()

        val apiKey = "17cb852da4c0e80bcf2a9f424281b8354f5468b9"
        val url = "https://api.github.com/users/$username/followers"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {

                val result = String(responseBody)
                Log.d("Exception", result)

                try {
                    val jsonArray = JSONArray(result)

                    for (i in 0 until jsonArray.length()){
                        val user = User()
                        val jsonObject = jsonArray.getJSONObject(i)
                        user.username = jsonObject.getString("login")
                        user.avatar = jsonObject.getString("avatar_url")
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
    fun getFollowers(): LiveData<ArrayList<User>> {
        return listUsers
    }
}