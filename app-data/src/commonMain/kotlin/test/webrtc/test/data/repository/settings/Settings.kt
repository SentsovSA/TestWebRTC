package test.webrtc.test.data.repository.settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.toSuspendSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal val settings = Settings()
internal val suspendSettings = settings.toSuspendSettings(Dispatchers.IO)

@Suppress("EnumEntryName")
internal enum class SettingsKeys {
    isDarkTheme,
    accessToken,
    refreshToken,
    phoneOrEmailVerificationId,
    pinCode,
    userNotificationChannels,
    biometricEnabled,
    userName
}