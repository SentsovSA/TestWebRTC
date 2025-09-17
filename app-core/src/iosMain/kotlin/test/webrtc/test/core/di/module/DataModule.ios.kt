package test.webrtc.test.core.di.module

import test.webrtc.test.database.AppDatabase
import test.webrtc.test.database.DatabaseFactory
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module

internal actual fun Module.appDatabase(): KoinDefinition<AppDatabase> =
    single<AppDatabase> {
        DatabaseFactory().create()
    }