package dev.pinaki.mubifotd.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build

interface AlarmScheduler {
    fun schedule(time: Long)
    fun cancelAlarm()
    fun isAlarmSet(): Boolean
}

class RealAlarmScheduler(
    private val context: Context
) : AlarmScheduler {
    override fun schedule(time: Long) {
        val resetReceiver = ComponentName(context, AlarmResetReceiver::class.java)
        context.packageManager.setComponentEnabledSetting(
            resetReceiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )

        val alarmIntent = pendingIntent(PendingIntent.FLAG_CANCEL_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager().setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                time,
                alarmIntent
            )
        } else {
            alarmManager().setExact(
                AlarmManager.RTC_WAKEUP,
                time,
                alarmIntent
            )
        }
    }

    private fun alarmManager() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private fun pendingIntent(flags: Int) = PendingIntent.getBroadcast(
        context,
        AlarmReceiver.REQUEST_CODE,
        Intent(context, AlarmReceiver::class.java),
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            PendingIntent.FLAG_IMMUTABLE or flags
        else
            PendingIntent.FLAG_NO_CREATE
    )

    override fun cancelAlarm() {
        val pendingIntent = pendingIntent(PendingIntent.FLAG_CANCEL_CURRENT)
        alarmManager().cancel(pendingIntent)
        pendingIntent.cancel()
    }

    override fun isAlarmSet() = pendingIntent(PendingIntent.FLAG_NO_CREATE) != null
}