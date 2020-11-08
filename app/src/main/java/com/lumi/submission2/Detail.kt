package com.lumi.submission2

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.progressBar

class Detail : AppCompatActivity() {

    companion object{
        const val EXTRA_USER = "username"
    }
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val user = intent.getParcelableExtra(EXTRA_USER) as User
        val imgPhoto: ImageView = findViewById(R.id.img_avatar)

        txt_username.text = user.username
        Glide.with(this)
            .load(user.avatar)
            .into(imgPhoto)


        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        user.username?.let { detailViewModel.setDetail(it) }
        showLoading(true)

        detailViewModel.getDetails().observe(this, { userDetail ->
            if (userDetail != null) {
                txt_name.text = userDetail.name
                txt_company.text = userDetail.company
                txt_location.text = userDetail.location
                txt_follower.text = userDetail.follower.toString()
                txt_following.text = userDetail.following.toString()
                showLoading(false)
            }
        })

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = user.username
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f
    }
    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.INVISIBLE
        }
    }
}