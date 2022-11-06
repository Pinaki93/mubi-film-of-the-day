package dev.pinaki.mubifotd.alarm.syncretry

import android.content.Context
import androidx.work.*
import timber.log.Timber

interface SyncRetryScheduler {
    fun schedule()
    fun isScheduled(): Boolean
    fun cancel()
}

class RealSyncRetryScheduler(private val context: Context) : SyncRetryScheduler {
    override fun schedule() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = OneTimeWorkRequestBuilder<SyncRetryWorker>()
            .setConstraints(constraints)
            .addTag(SyncRetryWorker.TAG)
            .build()
        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                SyncRetryWorker.TAG,
                ExistingWorkPolicy.REPLACE,
                workRequest
            )
    }

    override fun isScheduled(): Boolean {
        return try {
            WorkManager.getInstance(context)
                .getWorkInfosByTag(SyncRetryWorker.TAG)
                .get()
                .mapNotNull { it?.state }
                .any { it == WorkInfo.State.RUNNING || it == WorkInfo.State.ENQUEUED }
        } catch (e: Exception) {
            Timber.e(e)
            false
        }
    }

    override fun cancel() {
        WorkManager.getInstance(context)
            .cancelAllWorkByTag(SyncRetryWorker.TAG)
    }
}