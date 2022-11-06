package dev.pinaki.mubifotd.di.injector

import androidx.compose.runtime.Composable
import dev.pinaki.mubifotd.landing.di.LandingModule

class LandingUiInjector(private val landingModule: LandingModule) {
    val viewModel by lazy {
        landingModule.filmOfTheDayViewModel()
    }

    val viewController by lazy {
        landingModule.viewController
    }
}