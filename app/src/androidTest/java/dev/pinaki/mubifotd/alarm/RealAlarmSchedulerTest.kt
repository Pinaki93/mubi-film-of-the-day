package dev.pinaki.mubifotd.alarm

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class RealAlarmSchedulerTest {

    private val handler =
        RealAlarmScheduler(InstrumentationRegistry.getInstrumentation().targetContext)

    @Test
    fun test_noAlarmSet() {
        assert(!handler.isAlarmSet())
    }

    @Test
    fun test_setAlarm() {
        handler.schedule(
            System.currentTimeMillis() + TimeUnit.HOURS.toMicros(2)
        )
        assert(handler.isAlarmSet())
    }

    @Test
    fun test_cancelAlarm_noAlarmSet() {
        handler.schedule(
            System.currentTimeMillis() + TimeUnit.HOURS.toMicros(2)
        )
        handler.cancelAlarm()
        assert(!handler.isAlarmSet())
    }

    @After
    fun tearDown() {
        if (handler.isAlarmSet()) {
            handler.cancelAlarm()
        }
    }
}