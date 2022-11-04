package dev.pinaki.mubifotd.di

import android.annotation.SuppressLint
import android.content.Context
import dev.pinaki.mubifotd.di.module.*
import dev.pinaki.mubifotd.landing.di.LandingSubContainer
import kotlinx.coroutines.CoroutineScope

class RootContainer private constructor(private val context: Context) {

    private val dbModule by lazy { DbModule(context) }
    private val networkModule by lazy { NetworkModule(ParserModule.moshi) }
    private val sharedPreferencesModule by lazy { SharedPreferencesModule(context) }
    private val interactorModule by lazy {
        InteractorModule(
            networkModule.mubiClient,
            dbModule.movieStore,
            sharedPreferencesModule.appPreferences(),
            TimeProviderModule.timeProvider()
        )
    }

    fun landingSubContainer(coroutineScope: CoroutineScope) =
        LandingSubContainer(coroutineScope, interactorModule)

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: RootContainer? = null

        fun get(context: Context): RootContainer {
            if (instance == null) {
                synchronized(RootContainer::class) {
                    if (instance == null) {
                        instance = RootContainer(context.applicationContext)
                    }
                }
            }

            return instance!!
        }
    }
}