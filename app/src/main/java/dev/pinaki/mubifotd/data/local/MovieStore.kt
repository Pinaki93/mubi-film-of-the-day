package dev.pinaki.mubifotd.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.pinaki.mubifotd.domain.FilmOfTheDay
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieStore {
    @Insert
    suspend fun insert(movies: List<FilmOfTheDay>)

    @Query("select * from film_of_the_day")
    fun getAllAsFlow(): Flow<List<FilmOfTheDay>>

    @Query("select * from film_of_the_day")
    suspend fun getAll(): List<FilmOfTheDay>

    @Query("delete from film_of_the_day")
    suspend fun clearAll()
}