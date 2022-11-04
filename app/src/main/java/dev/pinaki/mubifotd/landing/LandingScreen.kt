package dev.pinaki.mubifotd.landing

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun LandingScreen(viewModel: FilmOfTheDayViewModel) {
    when (val state = viewModel.state) {
        FilmOfTheDayState.FatalError -> Text(text = "Fatal error")
        FilmOfTheDayState.Loading -> Text(text = "Loading")
        FilmOfTheDayState.Offline -> Text(text = "offline")
        is FilmOfTheDayState.ServerError -> Text(text = "server error (${state.errorCode}")
        is FilmOfTheDayState.Success -> Text(text = state.filmOfTheDay.title)
    }
}