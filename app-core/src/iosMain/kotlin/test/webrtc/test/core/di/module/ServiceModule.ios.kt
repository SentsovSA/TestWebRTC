package test.webrtc.test.core.di.module

import test.webrtc.test.core.interfaces.FileSaver
import test.webrtc.test.core.service.FirebaseServiceImpl
import test.webrtc.test.core.utils.IOSFileSaver
import org.koin.core.module.Module

internal actual fun Module.firebaseService(
    createdAtStart: Boolean
) = single<FirebaseService>(createdAtStart = createdAtStart) {
    FirebaseServiceImpl.create(
        settingsRepository = get(),
        firebaseMessagingRepository = get()
    )
}

internal actual fun Module.fileSaver() = single<FileSaver> {
    IOSFileSaver()
}
