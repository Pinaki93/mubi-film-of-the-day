package dev.pinaki.mubifotd.di.module

import android.content.Context
import dev.pinaki.mubifotd.data.local.AppPreferences

class SharedPreferencesModule(context: Context) {
    private val preferences by lazy {
        context.applicationContext.getSharedPreferences(
            context.packageName + "app_preferences",
            Context.MODE_PRIVATE
        )
    }

    fun appPreferences() = AppPreferences(preferences)
}