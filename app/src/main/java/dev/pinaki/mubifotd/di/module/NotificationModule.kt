package dev.pinaki.mubifotd.di.module

import android.content.Context
import dev.pinaki.mubifotd.notification.FilmOfTheDayChannel
import dev.pinaki.mubifotd.notification.NotificationHandler
import dev.pinaki.mubifotd.notification.RealNotificationHandler

class NotificationModule(private val context: Context) {

    private fun filmOfTheDayChannel() = FilmOfTheDayChannel(context)
    fun notificationHandler(): NotificationHandler =
        RealNotificationHandler(context, filmOfTheDayChannel())
}