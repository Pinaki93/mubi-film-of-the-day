package dev.pinaki.mubifotd.alarm.syncretry

import androidx.work.ListenableWorker.Result

enum class SyncRetryResult {
    SUCCESS, FAILURE, RETRY;

    fun toWorkResult() = when (this) {
        SUCCESS -> Result.success()
        FAILURE -> Result.failure()
        RETRY -> Result.retry()
    }
}