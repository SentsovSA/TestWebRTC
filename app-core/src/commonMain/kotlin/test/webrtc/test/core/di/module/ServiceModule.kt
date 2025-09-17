package test.webrtc.test.core.di.module

import test.webrtc.test.core.interfaces.FileSaver
import test.webrtc.test.domain.service.FirebaseService
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect fun Module.firebaseService(
    createdAtStart: Boolean
): KoinDefinition<FirebaseService>

internal expect fun Module.fileSaver(): KoinDefinition<FileSaver>

internal val serviceModule = module {
    /*firebaseService(createdAtStart = true)*/
    fileSaver()
}