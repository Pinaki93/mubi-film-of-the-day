package dev.pinaki.mubifotd.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import dev.pinaki.mubifotd.domain.FilmOfTheDay
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieStore {
    @Insert
    protected abstract suspend fun insert(movies: List<FilmOfTheDay>)

    @Query("select * from film_of_the_day")
    abstract fun getAllAsFlow(): Flow<List<FilmOfTheDay>>

    @Query("select * from film_of_the_day order by `order` asc")
    abstract suspend fun getAll(): List<FilmOfTheDay>

    @Query("delete from film_of_the_day")
    abstract suspend fun clearAll()

    @Transaction
    open suspend fun clearAndInsertAll(movies: List<FilmOfTheDay>) {
        clearAll()
        insert(movies)
    }
}