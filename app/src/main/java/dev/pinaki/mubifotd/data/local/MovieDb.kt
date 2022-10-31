package dev.pinaki.mubifotd.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.pinaki.mubifotd.domain.FilmOfTheDay


@Database(entities = [FilmOfTheDay::class], version = 1)
abstract class MovieDb : RoomDatabase() {
    abstract fun movieStore(): MovieStore
}