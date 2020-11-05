package com.lumi.submission2

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_detail.*
import org.json.JSONObject

class Detail : AppCompatActivity() {

    companion object{
        const val EXTRA_USER = "username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val user = intent.getParcelableExtra(EXTRA_USER) as User
        val imgPhoto: ImageView = findViewById(R.id.img_avatar)
        Glide.with(this)
            .load(user.avatar)
            .into(imgPhoto)
        setDetail()

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = user.username
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f
    }

    private fun setDetail() {

        val users = intent.getParcelableExtra(EXTRA_USER) as User
        val apiKey = ""
        val url = "https://api.github.com/users/${users.username}"
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
                    txt_username.text = user.username


                    txt_name.text = user.name
                    txt_company.text = user.company
                    txt_location.text = user.location
                    txt_follower.text = user.follower.toString()
                    txt_following.text = user.following.toString()

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
