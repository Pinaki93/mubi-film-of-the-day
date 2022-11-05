package dev.pinaki.mubifotd.common

import java.util.Calendar

object DateHelper {
    fun isSameDate(time1: Long, time2: Long): Boolean {
        val calendar1 = Calendar.getInstance().apply { timeInMillis = time1 }
        val calendar2 = Calendar.getInstance().apply { timeInMillis = time2 }

        return calendar1[Calendar.DAY_OF_MONTH] == calendar2[Calendar.DAY_OF_MONTH]
                && calendar1[Calendar.MONTH] == calendar2[Calendar.MONTH]
                && calendar1[Calendar.YEAR] == calendar2[Calendar.YEAR]
    }
}