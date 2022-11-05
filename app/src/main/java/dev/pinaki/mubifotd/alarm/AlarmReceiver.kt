package dev.pinaki.mubifotd.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dev.pinaki.mubifotd.di.InjectorFactory
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AlarmReceiver : BroadcastReceiver() {

    lateinit var alarmInteractor: AlarmInteractor

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return

        InjectorFactory.fromContext(context)
            .getAlarmInjector()
            .inject(this)

        GlobalScope.launch {
            alarmInteractor.executeAlarm()
        }
    }

    companion object {
        const val REQUEST_CODE = 1000
    }
}