package com.lumi.submission2.ui

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.lumi.submission2.viewmodel.DetailViewModel
import com.lumi.submission2.R
import com.lumi.submission2.adapter.SectionsPagerAdapter
import com.lumi.submission2.db.MappingHelper
import com.lumi.submission2.db.UserEntity
import com.lumi.submission2.model.User
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.progressBar

class Detail : AppCompatActivity(), View.OnClickListener {

    companion object{
        const val EXTRA_USER = "username"
    }

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var userEntity: UserEntity
    private var statusFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val user = intent.getParcelableExtra<Parcelable>(EXTRA_USER) as User
        val imgPhoto: ImageView = findViewById(R.id.img_avatar)

        fab_add.setOnClickListener(this)

        txt_username.text = user.username
        Glide.with(this)
            .load(user.avatar)
            .into(imgPhoto)


        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailViewModel::class.java)

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

        userEntity.id.let { detailViewModel.setFavoriteById(it) }

        detailViewModel.getFavoriteById().observe(this, {
            if (it.count >= 1) {
                statusFavorite = true
            }
            setStatusFavorite(statusFavorite)
        })


        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = user.username
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab_add -> {
                statusFavorite = !statusFavorite
                setFavorite(statusFavorite)
                setStatusFavorite(statusFavorite)

            }
        }
    }

    private fun setStatusFavorite(status: Boolean) {
        if (status)
            fab_add.setImageResource(R.drawable.ic_baseline_favorite_36)
        else
            fab_add.setImageResource(R.drawable.ic_outline_favorite_border_36)
    }

    private fun setFavorite(status: Boolean) {
        if (status) {
            val content = MappingHelper.convertToContentValues(userEntity)
            detailViewModel.setFavoriteUser(
                content
            )

            Toast.makeText(this, R.string.add_user, Toast.LENGTH_SHORT).show()
        } else {
            detailViewModel.deleteFavoriteUser(
                userEntity.id
            )
            Toast.makeText(this, R.string.del_user, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.INVISIBLE
        }
    }
}