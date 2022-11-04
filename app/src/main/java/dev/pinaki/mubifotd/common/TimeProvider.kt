package dev.pinaki.mubifotd.common

interface TimeProvider {
    fun currentTimeInMillis(): Long
}

class RealTimeProvider : TimeProvider {
    override fun currentTimeInMillis(): Long {
        return System.currentTimeMillis()
    }
}