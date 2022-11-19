package dev.pinaki.mubifotd.data.local

import com.squareup.moshi.Moshi
import dev.pinaki.mubifotd.common.filestorage.FileStore
import dev.pinaki.mubifotd.domain.FilmOfTheDay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface MovieStore {
    suspend fun getFilmOfTheDay(): FilmOfTheDay?
    suspend fun clear()
    suspend fun clearAndInsert(filmOfTheDay: FilmOfTheDay)
}

class RealMovieStore(
    private val fileStore: FileStore,
    private val moshi: Moshi,
) : MovieStore {

    private val mutex = Mutex()
    private val adapter by lazy {
        moshi.adapter(FilmOfTheDay::class.java)
    }

    override suspend fun getFilmOfTheDay(): FilmOfTheDay? {
        return fileStore.read(KEY_FILM_OF_THE_DAY)
            ?.let { adapter.fromJson(it) }
    }

    override suspend fun clear() {
        fileStore.read(KEY_FILM_OF_THE_DAY)
    }

    private suspend fun insert(value: FilmOfTheDay) {
        fileStore.write(KEY_FILM_OF_THE_DAY, adapter.toJson(value))
    }

    override suspend fun clearAndInsert(filmOfTheDay: FilmOfTheDay) {
        mutex.withLock {
            clear()
            insert(filmOfTheDay)
        }
    }

    companion object {
        private const val KEY_FILM_OF_THE_DAY = "fotd"
    }

}