package com.bash.githubsearchuser.service

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bash.githubsearchuser.ui.search.SearchActivity
import com.bash.githubsearchuser.util.ALARM_ID_REPEATING
import com.bash.githubsearchuser.util.ALARM_MESSAGE
import com.bash.githubsearchuser.util.ALARM_TITTLE

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val title = intent?.getStringExtra(ALARM_TITTLE)
        val message = intent?.getStringExtra(ALARM_MESSAGE)

        val notificationIntent = Intent(context, SearchActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)

        context?.let {
            AlarmHelper.showNotification(
                it,
                title ?: "Title",
                message ?: "Alarm Message",
                ALARM_ID_REPEATING,
                pendingIntent
            )
        }
    }

}