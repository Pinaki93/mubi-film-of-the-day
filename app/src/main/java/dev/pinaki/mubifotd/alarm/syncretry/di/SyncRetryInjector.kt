package dev.pinaki.mubifotd.alarm.syncretry.di

import android.content.Context
import dev.pinaki.mubifotd.di.RootContainer

class SyncRetryInjector(context: Context) {
    val alarmInteractor = RootContainer.get(context)
        .alarmModule
        .alarmInteractor()
}