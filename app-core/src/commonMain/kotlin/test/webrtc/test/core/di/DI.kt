package test.webrtc.test.core.di

import test.webrtc.test.core.di.module.*
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

internal fun initKoin(block: KoinAppDeclaration = {}) {
    startKoin {
        block()
        modules(
            networkModule,
            dataModule,
            repositoryModule,
            managerModule,
            serviceModule
        )
    }
}