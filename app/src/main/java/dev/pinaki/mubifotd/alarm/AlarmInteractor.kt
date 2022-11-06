package dev.pinaki.mubifotd.alarm

import dev.pinaki.mubifotd.alarm.syncretry.SyncRetryResult
import dev.pinaki.mubifotd.alarm.syncretry.SyncRetryScheduler
import dev.pinaki.mubifotd.alarm.syncretry.SyncRetryWorker
import dev.pinaki.mubifotd.common.TimeProvider
import dev.pinaki.mubifotd.data.remote.HttpClientResponse
import dev.pinaki.mubifotd.domain.FilmOfTheDay
import dev.pinaki.mubifotd.domain.usecase.FilmOfTheDayInteractor
import dev.pinaki.mubifotd.notification.NotificationHandler
import java.util.Calendar
import java.util.concurrent.TimeUnit

interface AlarmInteractor {
    fun alarmHourAndMinute(): AlarmTime
    fun scheduleAlarm()
    fun cancelAlarm()
    fun isAlarmSet(): Boolean
    suspend fun executeAlarm()
    suspend fun executeRetry(): SyncRetryResult
}

class RealAlarmInteractor(
    private val alarmScheduler: AlarmScheduler,
    private val timeProvider: TimeProvider,
    private val filmOfTheDayInteractor: FilmOfTheDayInteractor,
    private val notificationHandler: NotificationHandler,
    private val syncRetryScheduler: SyncRetryScheduler
) : AlarmInteractor {
    override fun alarmHourAndMinute(): AlarmTime {
        // hardcoding it to 8:00pm. In case I ever go ahead in writing more code for this,
        // I'll create a settings screen to customise this :p
        return AlarmTime(20, 0)
    }

    override fun scheduleAlarm() {
        val alarmTime = alarmHourAndMinute()
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timeProvider.currentTimeInMillis()
            this[Calendar.HOUR_OF_DAY] = alarmTime.hourOfTheDay
            this[Calendar.MINUTE] = alarmTime.minute
            this[Calendar.SECOND] = 0
            this[Calendar.MILLISECOND] = 0
        }
        var firstSetOffTime = calendar.timeInMillis
        if (timeProvider.currentTimeInMillis() > calendar.timeInMillis) {
            firstSetOffTime += TimeUnit.DAYS.toMillis(1)
        }
        alarmScheduler.schedule(firstSetOffTime)
    }

    override fun cancelAlarm() {
        alarmScheduler.cancelAlarm()
    }

    override fun isAlarmSet() = alarmScheduler.isAlarmSet()

    override suspend fun executeAlarm() {
        var filmOfTheDay: FilmOfTheDay? = null
        if (filmOfTheDayInteractor.syncRequired()) {
            val syncResult = filmOfTheDayInteractor.sync()
            if (syncResult is HttpClientResponse.Ok) {
                filmOfTheDay = syncResult.data.first()
            } else if (!filmOfTheDayInteractor.fatalStateReached()) {
                syncRetryScheduler.schedule()
            }
        } else {
            filmOfTheDay = filmOfTheDayInteractor.getFilmOfTheDay()
        }

        filmOfTheDay?.let { notificationHandler.notifyFilmOfTheDay(it) }
        if (!filmOfTheDayInteractor.fatalStateReached()) {
            scheduleAlarm()
        }
    }

    override suspend fun executeRetry(): SyncRetryResult {
        var filmOfTheDay: FilmOfTheDay? = null
        val retryResult = if (filmOfTheDayInteractor.syncRequired()) {
            val syncResult = filmOfTheDayInteractor.sync()
            if (syncResult is HttpClientResponse.Ok) {
                filmOfTheDay = syncResult.data.first()
                SyncRetryResult.SUCCESS
            } else if (filmOfTheDayInteractor.fatalStateReached()) {
                SyncRetryResult.FAILURE
            } else {
                SyncRetryResult.RETRY
            }
        } else {
            filmOfTheDay = filmOfTheDayInteractor.getFilmOfTheDay()
            SyncRetryResult.SUCCESS
        }

        filmOfTheDay?.let { notificationHandler.notifyFilmOfTheDay(it) }
        return retryResult
    }
}

data class AlarmTime(val hourOfTheDay: Int, val minute: Int)