package dev.pinaki.mubifotd.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilmOfTheDay(
    val id: Int,
    val filmId: Int,
    val title: String,
    val webUrl: String,
    val order: Int,
    val directors: List<String>,
    val year: Int,
    val synopsis: String
)