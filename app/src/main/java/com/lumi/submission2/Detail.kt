package com.lumi.submission2

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class Detail : AppCompatActivity() {

    companion object{
        const val EXTRA_USER = "extra_user"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val imgPhoto: ImageView = findViewById(R.id.img_avatar)
        val user = intent.getParcelableExtra(EXTRA_USER) as User

        txt_username.text = user.username
        Glide.with(this)
            .load(user.avatar)
            .into(imgPhoto)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f
    }
}