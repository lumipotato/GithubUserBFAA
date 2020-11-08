package com.lumi.submission2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.progressBar
import org.json.JSONObject

class Detail : AppCompatActivity() {

    companion object{
        const val EXTRA_USER = "username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        progressBar.visibility = View.VISIBLE
        val user = intent.getParcelableExtra(EXTRA_USER) as User
        val imgPhoto: ImageView = findViewById(R.id.img_avatar)
        txt_username.text = user.username
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
        val apiKey = "17cb852da4c0e80bcf2a9f424281b8354f5468b9"
        val url = "https://api.github.com/users/${users.username}"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {

                val result = String(responseBody)
                Log.d("Exception", result)
                progressBar.visibility = View.INVISIBLE

                try {
                    val responseObject = JSONObject(result)

                    val user = UserDetail()
                    user.username = responseObject.getString("login")
                    user.avatar = responseObject.getString("avatar_url")
                    user.name = responseObject.getString("name")
                    user.company = responseObject.getString("company")
                    user.location = responseObject.getString("location")
                    user.follower = responseObject.getInt("followers")
                    user.following = responseObject.getInt("following")


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
                progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@Detail, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}