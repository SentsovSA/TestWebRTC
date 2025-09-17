package test.webrtc.test.ui.common

import androidx.compose.runtime.Composable
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

@Composable
actual fun DialPhoneNumber(phoneNumber: String) {
    val normalized = normalizePhoneNumber(phoneNumber)
    val phone = NSURL.URLWithString("tel:$normalized")
    if (phone != null) {
        if (UIApplication.sharedApplication.canOpenURL(phone)) {
            UIApplication.sharedApplication().openURL(phone, options = mapOf<Any?, String>(), completionHandler = {})
        }
    }
}

fun normalizePhoneNumber(formatted: String): String {
    return formatted.filter { it.isDigit() }
        .let { digits -> if (digits.startsWith("7")) "+$digits" else digits }
}