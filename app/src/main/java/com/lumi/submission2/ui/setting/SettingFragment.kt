package com.lumi.submission2.ui.setting

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.lumi.submission2.R
import com.lumi.submission2.misc.AlarmReceiver

class SettingFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var languagePreference: Preference
    private lateinit var switchPreference: SwitchPreference
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
        clickLanguage()
    }

    private fun init() {
        switchPreference = findPreference<SwitchPreference>("reminder") as SwitchPreference
        languagePreference = findPreference<Preference>("language") as Preference

        alarmReceiver = AlarmReceiver()
    }

    private fun clickLanguage() {
        languagePreference.setOnPreferenceClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            true
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }
    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        if (key == "reminder") {
            val status = sharedPreferences.getBoolean("reminder", false)

            if (status) {
                alarmReceiver.setAlarmOn(context, "09:00")

            } else {
                alarmReceiver.setAlarmOff(context)

            }
        }
    }
}