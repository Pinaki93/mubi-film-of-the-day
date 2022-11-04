package dev.pinaki.mubifotd.data.local

import android.content.SharedPreferences
import androidx.core.content.edit

private const val FATAL_STATE_REACHED = "fatal_state_reached"
private const val ALARMS_SET = "alarms_set"
private const val LAST_SYNC_TIME = "last_sync_time"

class AppPreferences(private val sharedPreferences: SharedPreferences) {

    var fatalStateReached: Boolean
        get() = sharedPreferences.getBoolean(FATAL_STATE_REACHED, false)
        set(value) = sharedPreferences.edit { putBoolean(FATAL_STATE_REACHED, value) }

    var alarmsSet: Boolean
        get() = sharedPreferences.getBoolean(ALARMS_SET, false)
        set(value) = sharedPreferences.edit { putBoolean(ALARMS_SET, value) }

    var lastSyncTime: Long
        get() = sharedPreferences.getLong(LAST_SYNC_TIME, -1)
        set(value) = sharedPreferences.edit { putLong(LAST_SYNC_TIME, value) }


}