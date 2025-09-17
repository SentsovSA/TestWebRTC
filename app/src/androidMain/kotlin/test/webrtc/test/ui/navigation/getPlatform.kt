package test.webrtc.test.ui.navigation

import android.os.Build

actual fun getPlatform(): Platform = Platform.Android

actual fun getDeviceInfo(): String{
    return "Устройство: ${Build.MANUFACTURER} ${Build.MODEL}.\nОС: Android SDK ${Build.VERSION.SDK_INT}"
}