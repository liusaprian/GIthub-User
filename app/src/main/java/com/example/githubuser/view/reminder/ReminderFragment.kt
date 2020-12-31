package com.example.githubuser.view.reminder

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.githubuser.R

class ReminderFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var REMINDER: String
    private lateinit var switchPreference: SwitchPreference
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preference)

        REMINDER = resources.getString(R.string.switch_reminder)

        alarmReceiver = AlarmReceiver()

        switchPreference = findPreference<SwitchPreference>(REMINDER) as SwitchPreference
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sp: SharedPreferences, key: String) {
        if(key == REMINDER) {
            val reminderOn = sp.getBoolean(REMINDER, false)
            if(reminderOn) {
                alarmReceiver.setReminder(requireActivity())
                Toast.makeText(activity, getString(R.string.reminder_on), Toast.LENGTH_SHORT).show()
            }
            else {
                alarmReceiver.cancelAlarm(requireActivity())
                Toast.makeText(activity, getString(R.string.reminder_off), Toast.LENGTH_SHORT).show()
            }
        }
    }
}