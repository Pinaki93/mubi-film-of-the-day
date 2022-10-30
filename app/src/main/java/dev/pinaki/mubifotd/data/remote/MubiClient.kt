package dev.pinaki.mubifotd.data.remote

import com.squareup.moshi.Moshi
import dev.pinaki.mubifotd.domain.FilmOfTheDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import org.jsoup.Jsoup
import timber.log.Timber

private const val ENDPOINT = "https://mubi.com/film-of-the-day"

object ParsingError : HttpClientResponse<Nothing>

class MubiClient(private val okHttpClient: OkHttpClient, private val moshi: Moshi) {

    @Throws(IOException::class)
    suspend fun fetchMovies(): HttpClientResponse<List<FilmOfTheDay>> {
        return withContext(Dispatchers.IO) {
            val htmlResponse = try {
                okHttpClient.newCall(newRequest()).execute()
            } catch (e: IOException) {
                Timber.e(e)
                return@withContext HttpClientResponse.Offline
            }

            if (htmlResponse.isSuccessful) {
                htmlResponse.body?.string()
                    ?.let { fetchMoviesJson(it) }
                    ?.let { parseToResponseModel(it) }
                    ?.let { mapToFilmOfTheDay(it) }
                    ?.let { HttpClientResponse.Ok(it) }
                    ?: ParsingError

            } else {
                HttpClientResponse.HttpError(htmlResponse.code)
            }
        }
    }

    private fun newRequest() = Request.Builder()
        .url(ENDPOINT)
        .build()

    private fun parseToResponseModel(moviesJson: String): MovieOfTheDayResponse? {
        val adapter = moshi.adapter(MovieOfTheDayResponse::class.java)
        return adapter.fromJson(moviesJson)
    }

    private fun mapToFilmOfTheDay(movieOfTheDayResponse: MovieOfTheDayResponse): List<FilmOfTheDay> {
        val initialState = movieOfTheDayResponse.props.initialState
        val films = initialState.film.films

        return initialState.filmProgramming.filmProgrammings.mapNotNull { programInfo ->
            val filmInfo = films[programInfo.filmId.toString()]
            filmInfo?.let {
                FilmOfTheDay(
                    programInfo.id,
                    programInfo.filmId,
                    filmInfo.title,
                    filmInfo.stillUrl,
                    filmInfo.webUrl
                )
            }
        }.also { println("${it.size}") }
    }

    private fun fetchMoviesJson(html: String): String? {
        return Jsoup.parse(html).getElementsByTag("script")
            .firstOrNull { it.id() == "__NEXT_DATA__" }
            ?.data()
    }
}