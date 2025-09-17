package test.webrtc.test.database

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import co.touchlab.sqliter.DatabaseConfiguration

actual class DatabaseFactory {
    actual fun create(): AppDatabase = AppDatabase.create(
        NativeSqliteDriver(
            schema = Database.Schema,
            name = DATABASE_FILENAME,
            onConfiguration = {
                DatabaseConfiguration(
                    name = DATABASE_FILENAME,
                    version = DATABASE_VERSION,
                    create = {

                    },
                    upgrade = { db, oldVersion, newVersion ->
                    }
                )
            }
        )
    )
}
