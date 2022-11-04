package dev.pinaki.mubifotd.landing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.pinaki.mubifotd.data.remote.HttpClientResponse
import dev.pinaki.mubifotd.data.remote.ParsingError
import dev.pinaki.mubifotd.domain.FilmOfTheDay
import dev.pinaki.mubifotd.domain.usecase.FilmOfTheDayInteractor
import dev.pinaki.mubifotd.landing.FilmOfTheDayState.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class FilmOfTheDayViewModel(
    private val coroutineScope: CoroutineScope,
    private val filmOfTheDayInteractor: FilmOfTheDayInteractor,
) {

    var state by mutableStateOf<FilmOfTheDayState>(Loading)
        private set

    init {
        coroutineScope.launch {
            state = if (filmOfTheDayInteractor.syncRequiredOnAppLaunch()) {
                // start as loading
                when (val syncResult = filmOfTheDayInteractor.sync()) {
                    is HttpClientResponse.HttpError -> ServerError(syncResult.statusCode)
                    HttpClientResponse.Offline -> Offline
                    is HttpClientResponse.Ok -> Success(syncResult.data.first())
                    ParsingError -> FatalError
                }
            } else if (filmOfTheDayInteractor.fatalStateReached()) {
                FatalError
            } else {
                Success(filmOfTheDayInteractor.getFilmOfTheDay()!!)
            }
        }
    }
}


sealed class FilmOfTheDayState {
    object Loading : FilmOfTheDayState()
    data class Success(val filmOfTheDay: FilmOfTheDay) : FilmOfTheDayState()
    object Offline : FilmOfTheDayState()
    data class ServerError(val errorCode: Int) : FilmOfTheDayState()
    object FatalError : FilmOfTheDayState()
}