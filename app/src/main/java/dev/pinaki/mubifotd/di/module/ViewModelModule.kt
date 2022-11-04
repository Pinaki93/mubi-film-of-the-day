package dev.pinaki.mubifotd.di.module

import dev.pinaki.mubifotd.landing.FilmOfTheDayViewModel
import dev.pinaki.mubifotd.domain.usecase.FilmOfTheDayInteractor
import kotlinx.coroutines.CoroutineScope

class ViewModelModule(
    private val coroutineScope: CoroutineScope,
    private val filmOfTheDayInteractor: FilmOfTheDayInteractor
) {
    fun filmOfTheDayViewModel() = FilmOfTheDayViewModel(coroutineScope, filmOfTheDayInteractor)
}