package test.webrtc.test.ui.common

import androidx.compose.runtime.Composable
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

@Composable
actual fun GetLinkOpener(url: String){
    val nsUrl = NSURL.URLWithString(url)
    if (nsUrl != null) {
        UIApplication.sharedApplication().openURL(nsUrl, options = mapOf<Any?, String>(), completionHandler = {})
    }
}