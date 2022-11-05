package dev.pinaki.mubifotd.notification

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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

        val notification = NotificationCompat.Builder(context, filmOfTheDayChannel.id)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(context.getString(R.string.mubi_film_of_the_day))
            .setContentText(filmOfTheDay.title)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        NotificationManagerCompat.from(context)
            .notify(FILM_OF_THE_DAY_NOTIFICATION_ID, notification)
    }

    companion object {
        private const val FILM_OF_THE_DAY_NOTIFICATION_ID = 1
    }
}