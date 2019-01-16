package uk.co.victoriajanedavis.chatapp.presentation.common

sealed class StreamState<in T> {
    data class OnNext<T>(val content: T) : StreamState<T>()
    data class OnError(val throwable: Throwable) : StreamState<Any?>()
}