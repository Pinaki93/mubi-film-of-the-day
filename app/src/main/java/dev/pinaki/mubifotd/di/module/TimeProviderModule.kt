package dev.pinaki.mubifotd.di.module

import dev.pinaki.mubifotd.common.RealTimeProvider
import dev.pinaki.mubifotd.common.TimeProvider

object TimeProviderModule {
    fun timeProvider(): TimeProvider = RealTimeProvider()
}