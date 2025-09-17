package test.webrtc.test.core.interfaces

import test.webrtc.test.domain.interfaces.HttpExceptionCallback

internal interface InternalHttpExceptionCallbacks {

    val internalHttpExceptionCallbacks: Set<HttpExceptionCallback>

    suspend fun notifyHttpExceptionCallbacks(
        block: suspend (HttpExceptionCallback) -> Unit
    ) {
        for (callback in internalHttpExceptionCallbacks) block(callback)
    }

}