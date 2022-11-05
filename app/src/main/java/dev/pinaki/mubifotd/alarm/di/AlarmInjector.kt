package dev.pinaki.mubifotd.alarm.di

import dev.pinaki.mubifotd.alarm.AlarmReceiver
import dev.pinaki.mubifotd.alarm.AlarmResetReceiver
import dev.pinaki.mubifotd.di.module.AlarmModule

class AlarmInjector(private val alarmModule: AlarmModule) {

    fun inject(alarmReceiver: AlarmReceiver) {
        alarmReceiver.alarmInteractor = alarmModule.alarmInteractor()
    }

    fun inject(alarmResetReceiver: AlarmResetReceiver) {
        alarmResetReceiver.alarmInteractor = alarmModule.alarmInteractor()
    }
}