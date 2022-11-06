package dev.pinaki.mubifotd.landing.di

import android.content.Context
import dev.pinaki.mubifotd.alarm.AlarmInteractor
import dev.pinaki.mubifotd.domain.usecase.FilmOfTheDayInteractor
import dev.pinaki.mubifotd.landing.LandingScreenViewController
import dev.pinaki.mubifotd.landing.LandingScreenViewModel
import kotlinx.coroutines.CoroutineScope

class LandingModule(
    private val coroutineScope: CoroutineScope,
    private val filmOfTheDayInteractor: FilmOfTheDayInteractor,
    private val alarmInteractor: AlarmInteractor,
    private val localContext: Context
) {
    fun filmOfTheDayViewModel() =
        LandingScreenViewModel(coroutineScope, filmOfTheDayInteractor, alarmInteractor)

    val viewController
        get() = LandingScreenViewController(localContext)
}