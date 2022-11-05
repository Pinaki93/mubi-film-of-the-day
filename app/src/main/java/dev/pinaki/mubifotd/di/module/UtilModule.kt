package dev.pinaki.mubifotd.di.module

import dev.pinaki.mubifotd.common.DateHelper

object UtilModule {
    val dateHelper by lazy { DateHelper }
}