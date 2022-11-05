package dev.pinaki.mubifotd.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dev.pinaki.mubifotd.R

class FilmOfTheDayChannel(private val context: Context) {

    fun create() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        with(context) {
            val name = getString(R.string.fotd_channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(id, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    val id: String
        get() = "${context.packageName}.filmOfTheDay"
}