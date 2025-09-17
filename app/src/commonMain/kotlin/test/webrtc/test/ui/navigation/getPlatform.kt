package test.webrtc.test.ui.navigation

expect fun getPlatform(): Platform

expect fun getDeviceInfo(): String

enum class Platform {
    Android,
    iOS
}