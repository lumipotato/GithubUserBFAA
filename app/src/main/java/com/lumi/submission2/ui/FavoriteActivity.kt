package com.lumi.submission2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lumi.submission2.R
import com.lumi.submission2.adapter.ListUserAdapter
import com.lumi.submission2.db.UserEntity
import com.lumi.submission2.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.activity_main.*

class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: ListUserAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

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
                val moveIntent = Intent(this@FavoriteActivity, Detail::class.java)
                moveIntent.putExtra(Detail.EXTRA_USER, data)
                startActivity(moveIntent)
            }
        })

        favoriteViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FavoriteViewModel::class.java)

        favoriteViewModel.setFavoriteListUser(applicationContext)

        favoriteViewModel.getListFavorite().observe(this, { user ->
            if (user != null) {
                adapter.setData(user)
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

}