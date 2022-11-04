package dev.pinaki.mubifotd.landing.di

import dev.pinaki.mubifotd.di.module.InteractorModule
import kotlinx.coroutines.CoroutineScope

class LandingSubContainer(
    private val coroutineScope: CoroutineScope,
    private val interactorModule: InteractorModule
) {
    val landingModule by lazy {
        LandingModule(coroutineScope, interactorModule.filmOfTheDayInteractor())
    }
}