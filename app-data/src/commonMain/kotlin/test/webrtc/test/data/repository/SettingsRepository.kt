package test.webrtc.test.data.repository

import com.russhwolf.settings.ExperimentalSettingsApi
import test.webrtc.test.data.repository.settings.SettingsKeys
import test.webrtc.test.data.repository.settings.suspendSettings

interface SettingsRepository {

    companion object {
        fun create() : SettingsRepository = SettingsRepositoryImpl()
    }

    suspend fun isDarkTheme() : Boolean

    suspend fun setWatchedStory(id: String)
    suspend fun getWatchedStories(ids: Set<String>): Set<String>

    suspend fun setAccessToken(value: String)
    suspend fun getAccessToken() : String?
    suspend fun removeAccessToken()

    suspend fun setRefreshToken(value: String)
    suspend fun getRefreshToken() : String?
    suspend fun removeRefreshToken()

    suspend fun setPhoneOrEmailVerificationId(value: String)
    suspend fun getPhoneOrEmailVerificationId() : String?
    suspend fun removePhoneOrEmailVerificationId()

    suspend fun setPiCode(value: String)
    suspend fun getPiCode() : String?
    suspend fun removePiCode()

    suspend fun setBiometry(value: Boolean)
    suspend fun getBiometry() : Boolean?
    suspend fun removeBiometry()

    suspend fun setNotificationChannels(value: Boolean)
    suspend fun getNotificationChannels() : Boolean?
    suspend fun removeNotificationChannels()

    suspend fun setName(value: String)
    suspend fun getName() : String?
    suspend fun removeName()

}

@OptIn(ExperimentalSettingsApi::class)
private class SettingsRepositoryImpl : SettingsRepository {

    override suspend fun isDarkTheme() = suspendSettings.getBoolean(SettingsKeys.isDarkTheme.name, false)

    override suspend fun setWatchedStory(id: String) = suspendSettings.putString(id, id)

    override suspend fun getWatchedStories(ids: Set<String>): Set<String> {
        val watchedStories = mutableSetOf<String>()
        ids.forEach {
            val story = suspendSettings.getStringOrNull(it)
            if (story != null) {
                watchedStories.add(it)
            }
        }
        return watchedStories
    }

    override suspend fun setAccessToken(value: String) = suspendSettings.putString(SettingsKeys.accessToken.name, value)
    override suspend fun getAccessToken() = suspendSettings.getStringOrNull(SettingsKeys.accessToken.name)
    override suspend fun removeAccessToken() = suspendSettings.remove(SettingsKeys.accessToken.name)

    override suspend fun setRefreshToken(value: String) = suspendSettings.putString(SettingsKeys.refreshToken.name, value)
    override suspend fun getRefreshToken() = suspendSettings.getStringOrNull(SettingsKeys.refreshToken.name)
    override suspend fun removeRefreshToken() = suspendSettings.remove(SettingsKeys.refreshToken.name)

    override suspend fun setPhoneOrEmailVerificationId(value: String) = suspendSettings.putString(
        SettingsKeys.phoneOrEmailVerificationId.name, value)
    override suspend fun getPhoneOrEmailVerificationId() = suspendSettings.getStringOrNull(
        SettingsKeys.phoneOrEmailVerificationId.name)
    override suspend fun removePhoneOrEmailVerificationId() = suspendSettings.remove(SettingsKeys.phoneOrEmailVerificationId.name)

    override suspend fun setPiCode(value: String) = suspendSettings.putString(SettingsKeys.pinCode.name, value)
    override suspend fun getPiCode() = suspendSettings.getStringOrNull(SettingsKeys.pinCode.name)
    override suspend fun removePiCode() = suspendSettings.remove(SettingsKeys.pinCode.name)

    override suspend fun setNotificationChannels(value: Boolean) = suspendSettings.putBoolean(SettingsKeys.userNotificationChannels.name, value)
    override suspend fun getNotificationChannels() = suspendSettings.getBoolean(SettingsKeys.userNotificationChannels.name, false)
    override suspend fun removeNotificationChannels() = suspendSettings.remove(SettingsKeys.userNotificationChannels.name)

    override suspend fun setName(value: String) = suspendSettings.putString(SettingsKeys.userName.name, value)
    override suspend fun getName() = suspendSettings.getStringOrNull(SettingsKeys.userName.name)
    override suspend fun removeName() = suspendSettings.remove(SettingsKeys.userName.name)

    override suspend fun setBiometry(value: Boolean) = suspendSettings.putBoolean(SettingsKeys.biometricEnabled.name, value)
    override suspend fun getBiometry() = suspendSettings.getBoolean(SettingsKeys.biometricEnabled.name, false)
    override suspend fun removeBiometry() = suspendSettings.remove(SettingsKeys.biometricEnabled.name)

}