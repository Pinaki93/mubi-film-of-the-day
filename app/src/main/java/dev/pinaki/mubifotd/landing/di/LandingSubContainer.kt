package dev.pinaki.mubifotd.landing.di

import android.content.Context
import dev.pinaki.mubifotd.di.module.AlarmModule
import dev.pinaki.mubifotd.di.module.InteractorModule
import dev.pinaki.mubifotd.landing.LandingScreenViewController
import kotlinx.coroutines.CoroutineScope

class LandingSubContainer(
    private val coroutineScope: CoroutineScope,
    private val localContext: Context,
    private val interactorModule: InteractorModule,
    private val alarmModule: AlarmModule,
) {
    val landingModule by lazy {
        LandingModule(
            coroutineScope,
            interactorModule.filmOfTheDayInteractor(),
            alarmModule.alarmInteractor(),
            localContext
        )
    }
}