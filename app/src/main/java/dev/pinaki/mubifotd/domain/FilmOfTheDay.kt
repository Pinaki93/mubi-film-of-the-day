package dev.pinaki.mubifotd.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "film_of_the_day")
data class FilmOfTheDay(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "film_id")
    val filmId: Int,
    val title: String,
    @ColumnInfo(name = "web_url")
    val webUrl: String,
    val order: Int,
    val directors: List<String>,
    val year: Int,
    val synopsis: String
)