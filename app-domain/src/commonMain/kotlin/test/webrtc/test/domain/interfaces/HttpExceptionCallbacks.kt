package test.webrtc.test.domain.interfaces

interface HttpExceptionCallbacks {
    fun registerHttpExceptionCallback(callback: HttpExceptionCallback)
    fun unregisterHttpExceptionCallback(callback: HttpExceptionCallback)
}