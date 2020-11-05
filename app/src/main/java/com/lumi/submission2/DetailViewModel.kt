package com.lumi.submission2

import android.util.Log
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel:ViewModel() {

    fun setDetail(username: String) {

        val apiKey = "17cb852da4c0e80bcf2a9f424281b8354f5468b9"
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

                        val item = responseObject
                        val user = UserDetail()
                        user.username = item.getString("login")
                        user.avatar = item.getString("avatar_url")
                        user.name = item.getString("name")
                        user.company = item.getString("company")
                        user.location = item.getString("location")
                        user.follower = item.getInt("followers")
                        user.following = item.getInt("following")

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

}