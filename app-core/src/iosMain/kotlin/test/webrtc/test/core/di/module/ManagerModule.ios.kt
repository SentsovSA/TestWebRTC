package test.webrtc.test.core.di.module

import test.webrtc.test.core.manager.BiometryManagerImpl
import test.webrtc.test.core.manager.PlatformPermissionManagerImpl
import test.webrtc.test.core.manager.VibratorManagerImpl
import test.webrtc.test.domain.manager.BiometryManager
import test.webrtc.test.domain.manager.PlatformPermissionManager
import test.webrtc.test.domain.manager.VibratorManager
import org.koin.core.module.Module

internal actual fun Module.permissionManager() = single<PlatformPermissionManager> {
    PlatformPermissionManagerImpl()
}

internal actual fun Module.vibratorManager() = single<VibratorManager> {
    VibratorManagerImpl.create(
    )
}

internal actual fun Module.biometryManager() = single<BiometryManager> {
    BiometryManagerImpl.create(
        settingsRepository = get()
    )
}