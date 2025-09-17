package test.webrtc.test.core.di.module

import test.webrtc.test.database.AppDatabase
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect fun Module.appDatabase(): KoinDefinition<AppDatabase>

internal val dataModule = module {
    appDatabase()
}