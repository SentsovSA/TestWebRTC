package test.webrtc.test.core

actual fun isHostUnreachableException(e: Throwable): Boolean {
    return e.message?.contains("Request timeout has expired") == true || e.message?.contains("The Internet connection appears to be offline") == true
}