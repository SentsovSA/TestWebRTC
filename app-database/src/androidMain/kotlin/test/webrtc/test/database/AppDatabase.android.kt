package test.webrtc.test.database

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class DatabaseFactory(
    private val context: Context
) {
    actual fun create(): AppDatabase = AppDatabase.create(
        AndroidSqliteDriver(
            Database.Schema,
            context,
            DATABASE_FILENAME,
            callback = object : SupportSQLiteOpenHelper.Callback(DATABASE_VERSION) {
                override fun onCreate(db: SupportSQLiteDatabase) {
                }
                override fun onUpgrade(db: SupportSQLiteDatabase, oldVersion: Int, newVersion: Int) {
                }
            }
        )
    )
}
