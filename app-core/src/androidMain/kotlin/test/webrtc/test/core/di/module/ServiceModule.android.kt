package test.webrtc.test.core.di.module

import test.webrtc.test.core.interfaces.FileSaver
import test.webrtc.test.core.service.FirebaseServiceImpl
import test.webrtc.test.core.utils.AndroidFileSaver
import test.webrtc.test.domain.service.FirebaseService
import org.koin.core.module.Module

internal actual fun Module.firebaseService(
    createdAtStart: Boolean
) = single<FirebaseService>(
    createdAtStart = createdAtStart
) {
    FirebaseServiceImpl.create(
        context = get(),
        settingsRepository = get(),
    )
}

internal actual fun Module.fileSaver() = single<FileSaver> {
    AndroidFileSaver(context = get())
}