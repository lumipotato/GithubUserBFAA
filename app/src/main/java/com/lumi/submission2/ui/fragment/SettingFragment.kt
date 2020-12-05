package com.lumi.submission2.ui.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.lumi.submission2.R

class SettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }
}