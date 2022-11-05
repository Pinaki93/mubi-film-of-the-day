package dev.pinaki.mubifotd.landing.di

import dev.pinaki.mubifotd.di.module.AlarmModule
import dev.pinaki.mubifotd.di.module.InteractorModule
import kotlinx.coroutines.CoroutineScope

class LandingSubContainer(
    private val coroutineScope: CoroutineScope,
    private val interactorModule: InteractorModule,
    private val alarmModule: AlarmModule
) {
    val landingModule by lazy {
        LandingModule(
            coroutineScope,
            interactorModule.filmOfTheDayInteractor(),
            alarmModule.alarmInteractor()
        )
    }
}