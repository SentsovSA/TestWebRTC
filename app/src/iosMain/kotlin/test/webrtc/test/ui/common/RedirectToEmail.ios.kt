package test.webrtc.test.ui.common

import androidx.compose.runtime.Composable
import platform.Foundation.NSURL.Companion.URLWithString
import platform.UIKit.UIApplication

@Composable
actual fun RedirectToEmail(recipient: String, subject: String, body: String, isError: (Boolean) -> Unit) {
    val url = URLWithString("mailto:$recipient?subject=$subject&body=$body")!!
    UIApplication.sharedApplication().openURL(url, options = mapOf<Any?, String>()) {
            if (it) {
                print("Переход в приложение Почты выполнен успешно")
            } else {
                isError(true)
            }
    }
}