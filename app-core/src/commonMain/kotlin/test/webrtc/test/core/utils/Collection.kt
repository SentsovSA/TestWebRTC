package test.webrtc.test.core.utils

internal suspend fun <T> Iterable<T>.notify(
    block: suspend (T) -> Unit
) {
    for (element in this) block(element)
}