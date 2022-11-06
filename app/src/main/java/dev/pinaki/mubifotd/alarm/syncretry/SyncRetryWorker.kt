package dev.pinaki.mubifotd.alarm.syncretry

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dev.pinaki.mubifotd.alarm.AlarmInteractor
import dev.pinaki.mubifotd.alarm.syncretry.di.SyncRetryInjector

class SyncRetryWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    private val alarmInteractor: AlarmInteractor = SyncRetryInjector(context).alarmInteractor

    override suspend fun doWork(): Result {
        return alarmInteractor
            .executeRetry()
            .toWorkResult()
    }

    companion object {
        const val TAG = "SyncRetryWorkManager"
    }
}