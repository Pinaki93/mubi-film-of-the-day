package dev.pinaki.mubifotd.domain.usecase

import dev.pinaki.mubifotd.common.TimeProvider
import dev.pinaki.mubifotd.data.local.AppPreferences
import dev.pinaki.mubifotd.data.local.MovieStore
import dev.pinaki.mubifotd.data.remote.HttpClientResponse
import dev.pinaki.mubifotd.data.remote.MubiClient
import dev.pinaki.mubifotd.data.remote.ParsingError
import dev.pinaki.mubifotd.domain.FilmOfTheDay
import java.util.concurrent.TimeUnit

class FilmOfTheDayInteractor(
    private val client: MubiClient,
    private val store: MovieStore,
    private val preferences: AppPreferences,
    private val timeProvider: TimeProvider
) {
    suspend fun sync(): HttpClientResponse<List<FilmOfTheDay>> {
        val response = client.fetchMovies()
        if (response is HttpClientResponse.Ok) {
            store.clearAll()
            store.insert(response.data.reversed())
            preferences.lastSyncTime = timeProvider.currentTimeInMillis()
        }

        if (response is ParsingError) {
            store.clearAll()
            preferences.fatalStateReached = true
        }
        return response
    }

    fun getAllAsFlow() = store.getAllAsFlow()

    suspend fun getFilmOfTheDay() = store.getAll().firstOrNull()

    fun fatalStateReached() = preferences.fatalStateReached

    suspend fun syncRequiredOnAppLaunch() =
        getFilmOfTheDay() == null
                && preferences.fatalStateReached.not()
                && timeProvider.currentTimeInMillis() - preferences.lastSyncTime >
                TimeUnit.DAYS.toMillis(1)
}