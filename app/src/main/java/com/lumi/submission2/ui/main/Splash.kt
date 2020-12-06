package com.lumi.submission2.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.lumi.submission2.R

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.title = ""

        Handler(Looper.getMainLooper()).postDelayed(
            {startActivity(Intent(this@Splash, MainActivity::class.java))
                finish()
            },2000)
    }
}