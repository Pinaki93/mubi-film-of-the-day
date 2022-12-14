package dev.pinaki.mubifotd.domain.usecase

import dev.pinaki.mubifotd.common.DateHelper
import dev.pinaki.mubifotd.common.TimeProvider
import dev.pinaki.mubifotd.data.local.AppPreferences
import dev.pinaki.mubifotd.data.local.MovieStore
import dev.pinaki.mubifotd.data.remote.HttpClientResponse
import dev.pinaki.mubifotd.data.remote.MubiClient
import dev.pinaki.mubifotd.data.remote.ParsingError
import dev.pinaki.mubifotd.domain.FilmOfTheDay

class FilmOfTheDayInteractor(
    private val client: MubiClient,
    private val store: MovieStore,
    private val preferences: AppPreferences,
    private val timeProvider: TimeProvider,
    private val dateHelper: DateHelper
) {
    suspend fun sync(): HttpClientResponse<List<FilmOfTheDay>> {
        val response = client.fetchMovies()
        if (response is HttpClientResponse.Ok) {
            store.clearAndInsert(response.data.first())
            preferences.lastSyncTime = timeProvider.currentTimeInMillis()
        }

        if (response is ParsingError) {
            store.clear()
            preferences.fatalStateReached = true
        }
        return response
    }

    suspend fun getFilmOfTheDay() = store.getFilmOfTheDay()

    fun fatalStateReached() = preferences.fatalStateReached

    suspend fun syncRequired() =
        preferences.fatalStateReached.not() && (getFilmOfTheDay() == null || !dateHelper.isSameDate(
            preferences.lastSyncTime,
            timeProvider.currentTimeInMillis()
        ))
}