package dev.pinaki.mubifotd.di

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.pinaki.mubifotd.di.injector.LandingUiInjector
import kotlinx.coroutines.CoroutineScope

class InjectorFactory(private val rootContainer: RootContainer) {
    @Composable
    fun rememberLandingUiInjector(
        coroutineScope: CoroutineScope
    ) = remember(coroutineScope) {
        LandingUiInjector(
            rootContainer.landingSubContainer(coroutineScope).landingModule
        )
    }

    companion object {
        fun fromContext(context: Context) = InjectorFactory(RootContainer.get(context))
    }
}