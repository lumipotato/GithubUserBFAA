package com.lumi.submission2.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lumi.submission2.R
import com.lumi.submission2.ui.fragment.SettingFragment

class SettingHolder : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_holder)

        supportFragmentManager.beginTransaction().add(R.id.setting_holder, SettingFragment()).commit()
    }
}