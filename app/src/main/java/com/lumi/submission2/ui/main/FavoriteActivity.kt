package com.lumi.submission2.ui.main

import android.content.Intent
import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lumi.submission2.R
import com.lumi.submission2.adapter.ListUserAdapter
import com.lumi.submission2.db.UserEntity
import com.lumi.submission2.db.UserProvider.Companion.CONTENT_URI
import com.lumi.submission2.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.activity_main.*

class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: ListUserAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        rv_users.setHasFixedSize(true)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()
        rv_users.layoutManager = LinearLayoutManager(this)
        rv_users.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserEntity) {
                val moveIntent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                moveIntent.putExtra(DetailActivity.EXTRA_USER, data)
                startActivity(moveIntent)
            }
        })

        favoriteViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FavoriteViewModel::class.java)

        val handler = Handler(Looper.getMainLooper())
        val observer = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadListData()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, observer)

        if (savedInstanceState == null) {
            loadListData()
        } else {
            val list = savedInstanceState.getParcelableArrayList<UserEntity>(EXTRA_STATE)
            if (list != null) {
                adapter.mData = list
            }
        }

        supportActionBar?.title = getString(R.string.favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun loadListData() {
        favoriteViewModel.setFavoriteListUser(applicationContext)

        favoriteViewModel.getListFavorite().observe(this, { user ->
            if (user != null) {
                adapter.mData =  user
            }

            if (user.isNotEmpty()){
                text_not_found.visibility = View.GONE
            }
            else{
                text_not_found.visibility = View.VISIBLE
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.mData)
    }

}