package dev.pinaki.mubifotd.di.module

import android.content.Context
import dev.pinaki.mubifotd.alarm.AlarmScheduler
import dev.pinaki.mubifotd.alarm.AlarmInteractor
import dev.pinaki.mubifotd.alarm.RealAlarmScheduler
import dev.pinaki.mubifotd.alarm.RealAlarmInteractor
import dev.pinaki.mubifotd.alarm.syncretry.RealSyncRetryScheduler
import dev.pinaki.mubifotd.alarm.syncretry.SyncRetryScheduler
import dev.pinaki.mubifotd.common.TimeProvider
import dev.pinaki.mubifotd.domain.usecase.FilmOfTheDayInteractor
import dev.pinaki.mubifotd.notification.NotificationHandler

class AlarmModule(
    private val context: Context,
    private val timeProvider: TimeProvider,
    private val filmOfTheDayInteractor: FilmOfTheDayInteractor,
    private val notificationHandler: NotificationHandler
) {

    private fun alarmScheduler(): AlarmScheduler = RealAlarmScheduler(context)

    private fun syncRetryScheduler(): SyncRetryScheduler = RealSyncRetryScheduler(context)

    fun alarmInteractor(): AlarmInteractor =
        RealAlarmInteractor(
            alarmScheduler(),
            timeProvider,
            filmOfTheDayInteractor,
            notificationHandler,
            syncRetryScheduler()
        )
}