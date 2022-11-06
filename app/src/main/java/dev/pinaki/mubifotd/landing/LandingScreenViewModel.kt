package dev.pinaki.mubifotd.landing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.pinaki.mubifotd.alarm.AlarmInteractor
import dev.pinaki.mubifotd.data.remote.HttpClientResponse
import dev.pinaki.mubifotd.data.remote.ParsingError
import dev.pinaki.mubifotd.domain.FilmOfTheDay
import dev.pinaki.mubifotd.domain.usecase.FilmOfTheDayInteractor
import dev.pinaki.mubifotd.landing.FilmOfTheDayState.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class LandingScreenViewModel(
    private val coroutineScope: CoroutineScope,
    private val filmOfTheDayInteractor: FilmOfTheDayInteractor,
    alarmInteractor: AlarmInteractor
) {

    var state by mutableStateOf<FilmOfTheDayState>(Loading)
        private set

    init {
        coroutineScope.launch {
            state = if (filmOfTheDayInteractor.syncRequired()) {
                syncFilmOfTheDay()
            } else if (filmOfTheDayInteractor.fatalStateReached()) {
                FatalError
            } else {
                Success(filmOfTheDayInteractor.getFilmOfTheDayList())
            }
        }

        if (!alarmInteractor.isAlarmSet()) {
            alarmInteractor.scheduleAlarm()
        }
    }

    private suspend fun syncFilmOfTheDay() = when (val syncResult = filmOfTheDayInteractor.sync()) {
        is HttpClientResponse.HttpError -> ServerError(syncResult.statusCode)
        HttpClientResponse.Offline -> Offline
        is HttpClientResponse.Ok -> Success(filmOfTheDayInteractor.getFilmOfTheDayList())
        ParsingError -> FatalError
    }

    fun retry() {
        coroutineScope.launch {
            state = Loading
            state = syncFilmOfTheDay()
        }
    }
}


sealed class FilmOfTheDayState {
    object Loading : FilmOfTheDayState()
    class Success(filmOfTheDayList: List<FilmOfTheDay>) : FilmOfTheDayState() {
        val filmOfTheDay = filmOfTheDayList.first()
        private val year = filmOfTheDay.year
        val title: String = "${filmOfTheDay.title} ($year)"
        val directors = filmOfTheDay.directors.joinToString()
        val synopsis = filmOfTheDay.synopsis
        val showViewOlderButton = filmOfTheDayList.size > 1
        val shareText =
            "Today's Movie of the day:\n ${filmOfTheDay.title} ($year) by $directors\n ${filmOfTheDay.webUrl}"
        val url = filmOfTheDay.webUrl
    }

    object Offline : FilmOfTheDayState()
    data class ServerError(val errorCode: Int) : FilmOfTheDayState()
    object FatalError : FilmOfTheDayState()
}