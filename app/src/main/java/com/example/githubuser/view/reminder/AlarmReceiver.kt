package com.example.githubuser.view.reminder

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.githubuser.R
import com.example.githubuser.view.SplashActivity
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        private const val REMIND_HOUR = 9
        private const val ALARM_ID = 101
    }

    override fun onReceive(context: Context, intent: Intent) {
        showNotification(context)
        setReminder(context)
    }

    fun setReminder(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, this::class.java)

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, REMIND_HOUR)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        if(calendar.time <= Calendar.getInstance().time) calendar.add(Calendar.DAY_OF_MONTH, 1)

        val pendingIntent = PendingIntent.getBroadcast(context, ALARM_ID, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, this::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ALARM_ID, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)
    }

    private fun showNotification(context: Context) {
        val channelId = "channel"
        val channelName = "reminder"

        val title = context.resources.getString(R.string.reminder)
        val message = context.resources.getString(R.string.reminder_message)

        val bringForthLatestTaskIntent = Intent(context, SplashActivity::class.java)
            .setAction(Intent.ACTION_MAIN)
            .addCategory(Intent.CATEGORY_LAUNCHER)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val notificationIntent = PendingIntent.getActivity(context, 0, bringForthLatestTaskIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_baseline_alarm_24)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(notificationIntent)
            .setAutoCancel(true)
            .setSound(alarmSound)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(ALARM_ID, notification)
    }
}