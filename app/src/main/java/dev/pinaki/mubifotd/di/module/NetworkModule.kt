package dev.pinaki.mubifotd.di.module

import com.squareup.moshi.Moshi
import dev.pinaki.mubifotd.data.remote.MubiClient
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class NetworkModule(private val moshi: Moshi) {
    private val okhttpClient by lazy {
        OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    val mubiClient by lazy {
        MubiClient(okhttpClient, moshi)
    }
}