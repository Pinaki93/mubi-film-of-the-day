package dev.pinaki.mubifotd.data.remote

sealed interface HttpClientResponse<out T> {
    data class Ok<T>(val data: T) : HttpClientResponse<T>
    object Offline : HttpClientResponse<Nothing>
    data class HttpError(val statusCode: Int) : HttpClientResponse<Nothing>
}