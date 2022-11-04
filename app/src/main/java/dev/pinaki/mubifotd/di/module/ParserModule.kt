package dev.pinaki.mubifotd.di.module

import com.squareup.moshi.Moshi

object ParserModule {
    val moshi by lazy { Moshi.Builder().build() }
}