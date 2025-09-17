package test.webrtc.test.context

import androidx.compose.runtime.staticCompositionLocalOf

actual val LocalPlatformContext =
    staticCompositionLocalOf {
        PlatformContext.INSTANCE
    }