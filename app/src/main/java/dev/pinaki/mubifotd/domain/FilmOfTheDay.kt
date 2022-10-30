package dev.pinaki.mubifotd.domain

data class FilmOfTheDay(
    val id: Int,
    val filmId: Int,
    val title: String,
    val imageUrl: String,
    val webUrl: String
)