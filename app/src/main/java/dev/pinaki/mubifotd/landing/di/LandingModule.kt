package dev.pinaki.mubifotd.landing.di

import dev.pinaki.mubifotd.domain.usecase.FilmOfTheDayInteractor
import dev.pinaki.mubifotd.landing.FilmOfTheDayViewModel
import kotlinx.coroutines.CoroutineScope

class LandingModule(
    private val coroutineScope: CoroutineScope,
    private val filmOfTheDayInteractor: FilmOfTheDayInteractor
) {
    fun filmOfTheDayViewModel() = FilmOfTheDayViewModel(coroutineScope, filmOfTheDayInteractor)
}