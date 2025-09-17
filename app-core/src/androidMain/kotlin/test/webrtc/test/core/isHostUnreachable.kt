package test.webrtc.test.core

actual fun isHostUnreachableException(e: Throwable): Boolean {
    return e is java.net.UnknownHostException || e is java.util.concurrent.CancellationException
}