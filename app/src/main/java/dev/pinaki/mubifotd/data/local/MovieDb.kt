package dev.pinaki.mubifotd.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import dev.pinaki.mubifotd.domain.FilmOfTheDay


@Database(entities = [FilmOfTheDay::class], version = 1)
@TypeConverters(RoomConverters::class)
abstract class MovieDb : RoomDatabase() {
    abstract fun movieStore(): MovieStore
}