package dev.pinaki.mubifotd.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dev.pinaki.mubifotd.MainActivity
import dev.pinaki.mubifotd.R
import dev.pinaki.mubifotd.domain.FilmOfTheDay

interface NotificationHandler {
    fun notifyFilmOfTheDay(filmOfTheDay: FilmOfTheDay)
}

class RealNotificationHandler(
    private val context: Context,
    private val filmOfTheDayChannel: FilmOfTheDayChannel
) : NotificationHandler {

    override fun notifyFilmOfTheDay(filmOfTheDay: FilmOfTheDay) {
        filmOfTheDayChannel.create()

        val contentTextBuilder = StringBuilder(filmOfTheDay.title)
        if (filmOfTheDay.year > 0) {
            contentTextBuilder.append(" (${filmOfTheDay.year})")
        }
        if (filmOfTheDay.directors.isNotEmpty()) {
            contentTextBuilder.append(" by ${filmOfTheDay.directors.joinToString()}")
        }

        val appOpenIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            appOpenIntent,
            flags
        )

        val notification = NotificationCompat.Builder(context, filmOfTheDayChannel.id)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(context.getString(R.string.mubi_film_of_the_day))
            .setContentText(contentTextBuilder.toString())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        NotificationManagerCompat.from(context)
            .notify(FILM_OF_THE_DAY_NOTIFICATION_ID, notification)
    }

    companion object {
        private const val FILM_OF_THE_DAY_NOTIFICATION_ID = 1
    }
}