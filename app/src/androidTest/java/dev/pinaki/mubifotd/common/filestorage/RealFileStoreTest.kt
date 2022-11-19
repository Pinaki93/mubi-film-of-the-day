package dev.pinaki.mubifotd.common.filestorage

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@RunWith(AndroidJUnit4::class)
class RealFileStoreTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val store = RealFileStore(context.filesDir)

    @Test
    fun shouldWriteValue_andReadFromCacheAndFile(): Unit = runBlocking {
        store.write("key", "value")
        assert(store.read("key") == "value")

        RealFileStore(context.filesDir).read("key")
    }


    @Test
    fun shouldPutValueThreadSafe() {
        val executorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
        val countDownLatch = CountDownLatch(100)
        val lock = Unit
        var lastValue = -1
        for (i in (0 until 100)) {
            executorService.execute {
                runBlocking {
                    store.write("key", i.toString())
                    synchronized(Unit) {
                        lastValue = i
                    }
                    countDownLatch.countDown()
                }
            }
        }

        countDownLatch.await()
        runBlocking {
            val value = store.read("key")
            assert(value?.toIntOrNull() == 100) {
                "value instead is $value"
            }
        }
    }

    @After
    fun tearDown() {
        runBlocking {
            store.removeAll()
        }
    }
}