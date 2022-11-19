package dev.pinaki.mubifotd.di.module

import android.content.Context
import dev.pinaki.mubifotd.common.filestorage.FileStore
import dev.pinaki.mubifotd.common.filestorage.RealFileStore

class FileStoreModule(private val context: Context) {
    val fileStore: FileStore by lazy { RealFileStore(context.filesDir) }
}