package dev.pinaki.mubifotd.di.module

import android.content.Context
import com.squareup.moshi.Moshi
import dev.pinaki.mubifotd.common.filestorage.RealFileStore
import dev.pinaki.mubifotd.data.local.RealMovieStore

class PersistenceModule(context: Context, moshi: Moshi) {

    private val fileStore by lazy { RealFileStore(context.filesDir) }
    val store by lazy { RealMovieStore(fileStore, moshi) }

    companion object {
        private const val DB_NAME = "movie_db"
    }
}