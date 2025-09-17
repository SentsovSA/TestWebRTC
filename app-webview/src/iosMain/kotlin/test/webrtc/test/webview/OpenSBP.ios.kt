package test.webrtc.test.webview

import androidx.compose.runtime.Composable
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

@Composable
actual fun OpenSbp(url: String) {
    try {
        UIApplication.sharedApplication.openURL(NSURL.URLWithString(url)!!, options = emptyMap<Any?, Any>(), completionHandler = null)
    } catch (e: Exception) {

    }
}