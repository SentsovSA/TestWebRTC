package test.webrtc.test.core.di.module

import test.webrtc.test.core.manager.ConnectivityManagerImpl
import test.webrtc.test.core.manager.PinCodeManagerImpl
import test.webrtc.test.core.manager.ThemeManagerImpl
import test.webrtc.test.domain.manager.BiometryManager
import test.webrtc.test.domain.manager.ConnectivityManager
import test.webrtc.test.domain.manager.PlatformPermissionManager
import test.webrtc.test.domain.manager.PinCodeManager
import test.webrtc.test.domain.manager.ThemeManager
import test.webrtc.test.domain.manager.VibratorManager
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect fun Module.permissionManager(): KoinDefinition<PlatformPermissionManager>

internal expect fun Module.vibratorManager(): KoinDefinition<VibratorManager>

internal expect fun Module.biometryManager(): KoinDefinition<BiometryManager>

internal val managerModule = module {
    permissionManager()
    vibratorManager()
    biometryManager()
    single<ThemeManager> {
        ThemeManagerImpl.create(
            settingsRepository = get()
        )
    }
    single<ConnectivityManager> {
        ConnectivityManagerImpl.create()
    }
    single<PinCodeManager> {
        PinCodeManagerImpl.create(
            settingsRepository = get()
        )
    }
}