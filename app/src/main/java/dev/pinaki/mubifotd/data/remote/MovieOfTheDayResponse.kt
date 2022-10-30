package dev.pinaki.mubifotd.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MovieOfTheDayResponse(val props: Props) {
    @JsonClass(generateAdapter = true)
    class Props(val initialState: InitialState) {
        @JsonClass(generateAdapter = true)
        class InitialState(val filmProgramming: FilmProgramming, val film: Film) {
            @JsonClass(generateAdapter = true)
            class FilmProgramming(val filmProgrammings: List<FilmProgrammingInfo>) {
                @JsonClass(generateAdapter = true)
                class FilmProgrammingInfo(val id: Int, val filmId: Int)
            }

            @JsonClass(generateAdapter = true)
            class Film(val films: Map<String, FilmInfo>) {
                @JsonClass(generateAdapter = true)
                class FilmInfo(
                    val title: String,
                    @Json(name = "short_synopsis") val shortSynopsis: String,
                    @Json(name = "web_url") val webUrl: String,
                    @Json(name = "still_url") val stillUrl: String
                )
            }
        }
    }
}