package dev.pinaki.mubifotd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import dev.pinaki.mubifotd.di.InjectorFactory
import dev.pinaki.mubifotd.landing.LandingScreen
import dev.pinaki.mubifotd.ui.theme.MubiFilmOfTheDayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MubiFilmOfTheDayTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel = InjectorFactory.fromContext(LocalContext.current)
                        .rememberLandingUiInjector(coroutineScope = rememberCoroutineScope())
                        .viewModel()
                    LandingScreen(viewModel)
                }
            }
        }
    }
}