package test.webrtc.test.ui.utils

object PlatformDeeplinkHolder {
    var deeplinkUrl: String? = null
}

fun getInitialDeeplink(): String? {
    return PlatformDeeplinkHolder.deeplinkUrl
}