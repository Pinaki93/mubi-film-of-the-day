package dev.pinaki.mubifotd.di.module

import dev.pinaki.mubifotd.common.TimeProvider
import dev.pinaki.mubifotd.data.local.AppPreferences
import dev.pinaki.mubifotd.data.local.MovieStore
import dev.pinaki.mubifotd.data.remote.MubiClient
import dev.pinaki.mubifotd.domain.usecase.FilmOfTheDayInteractor

class InteractorModule(
    private val client: MubiClient,
    private val movieStore: MovieStore,
    private val appPreferences: AppPreferences,
    private val timeProvider: TimeProvider
) {
    fun filmOfTheDayInteractor() =
        FilmOfTheDayInteractor(client, movieStore, appPreferences, timeProvider)
}