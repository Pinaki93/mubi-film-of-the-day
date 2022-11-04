package dev.pinaki.mubifotd.di.module

import android.content.Context
import androidx.room.Room
import dev.pinaki.mubifotd.data.local.MovieDb

class DbModule(context: Context) {

    private val db by lazy {
        Room.databaseBuilder(context.applicationContext, MovieDb::class.java, DB_NAME).build()
    }

    val movieStore by lazy {
        db.movieStore()
    }

    companion object {
        private const val DB_NAME = "movie_db"
    }
}