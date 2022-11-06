package dev.pinaki.mubifotd.landing

import android.content.Context
import android.content.Intent

class LandingScreenViewController(val context: Context) {
    fun share(text: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }
}