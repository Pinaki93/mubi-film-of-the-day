package dev.pinaki.mubifotd.common

import android.app.Notification
import android.content.Context
import android.os.Build

fun getNotificationBuilder(context: Context, channelid: String) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Notification.Builder(context, channelid)
    } else {
        Notification.Builder(context)
    }