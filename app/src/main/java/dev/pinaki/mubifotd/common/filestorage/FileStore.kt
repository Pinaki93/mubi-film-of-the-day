package dev.pinaki.mubifotd.common.filestorage

import androidx.core.util.AtomicFile
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File
import java.io.IOException

interface FileStore {
    @Throws(IOException::class)
    suspend fun write(key: String, value: String)

    @Throws(IOException::class)
    suspend fun read(key: String): String?

    @Throws(IOException::class)
    suspend fun remove(key: String)

    @Throws(IOException::class)
    suspend fun removeAll()
}


@OptIn(DelicateCoroutinesApi::class)
class RealFileStore(directory: File) : FileStore {

    private val cache = mutableMapOf<String, String>()
    private val storeDirectory = File(directory.absolutePath + "/store")
    private val mutex = Mutex()

    init {
        GlobalScope.launch(Dispatchers.IO) {
            mutex.withLock {
                storeDirectory.mkdir()
            }
        }
    }

    @Throws(IOException::class)
    override suspend fun write(key: String, value: String) {
        mutex.withLock {
            withContext(Dispatchers.IO) {
                val atomicFile = AtomicFile(File(storeDirectory, key))
                val outputStream = atomicFile.startWrite()
                outputStream.write(value.toByteArray())
                atomicFile.finishWrite(outputStream)
            }
        }
    }

    @Throws(IOException::class)
    override suspend fun read(key: String): String? {
        mutex.withLock {
            return withContext(Dispatchers.IO) {
                val cachedValue = cache[key]
                if (cachedValue != null) return@withContext cachedValue

                val file = File(storeDirectory, key)
                if (file.exists()) {
                    val atomicFile = AtomicFile(file)
                    atomicFile.readFully().decodeToString()
                } else {
                    null
                }
            }
        }
    }

    @Throws(IOException::class)
    override suspend fun remove(key: String) {
        mutex.withLock {
            return withContext(Dispatchers.IO) {
                val file = File(storeDirectory, key)
                if (file.exists()) {
                    val atomicFile = AtomicFile(file)
                    atomicFile.delete()
                }
                cache.remove(key)
            }
        }
    }

    @Throws(IOException::class)
    override suspend fun removeAll() {
        return withContext(Dispatchers.IO) {
            mutex.withLock {
                storeDirectory.deleteRecursively()
                cache.clear()
            }
        }
    }
}