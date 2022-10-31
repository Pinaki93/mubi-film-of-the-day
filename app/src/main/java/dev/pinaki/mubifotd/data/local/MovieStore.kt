package dev.pinaki.mubifotd.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.pinaki.mubifotd.domain.FilmOfTheDay

@Dao
interface MovieStore {
    @Insert
    fun insert(movies: List<FilmOfTheDay>)

    @Query("select * from film_of_the_day")
    fun all(): List<FilmOfTheDay>

    @Query("delete from film_of_the_day")
    fun clearAll()
}