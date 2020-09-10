package com.bash.githubsearchuser.ui.setting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.bash.githubsearchuser.R
import com.bash.githubsearchuser.service.AlarmHelper
import com.bash.githubsearchuser.util.ALARM_ID_REPEATING
import com.bash.githubsearchuser.util.SP_KEY_REMINDER
import com.bash.githubsearchuser.util.SP_NAME
import kotlinx.android.synthetic.main.activity_setting.*
import java.util.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        sharedPreferences = this.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

        btn_back.setOnClickListener{
            onBackPressed()
        }
        switch_alarm.setOnCheckedChangeListener{ _, isChecked ->
            setAlarm(isChecked)
        }
        img_language.setOnClickListener{
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
        tv_change_language.setOnClickListener{
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
        tv_language.setOnClickListener{
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
        switch_alarm.apply {
            isChecked = sharedPreferences.getBoolean(SP_KEY_REMINDER, true)

            setOnCheckedChangeListener{ _, isChecked ->
                setAlarm(isChecked)
            }
        }
    }

    private fun setAlarm(isChecked: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(SP_KEY_REMINDER, isChecked)
        }.apply()
        if (isChecked) {
            AlarmHelper.enabledAlarm(
                this,
                getString(R.string.app_name),
                "Let's find popular user on Github!",
                ALARM_ID_REPEATING,
                Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 9)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                }
            ).apply {
                tv_daily_reminder_status.text = getString(R.string.daily_reminder_on)
            }

        } else {
            AlarmHelper.disabledAlarm(this, ALARM_ID_REPEATING).apply {
                tv_daily_reminder_status.text = getString(R.string.daily_reminder_off)
            }
        }

    }
}