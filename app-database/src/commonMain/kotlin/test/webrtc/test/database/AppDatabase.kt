package test.webrtc.test.database

import app.cash.sqldelight.db.SqlDriver
import test.webrtc.test.database.countryCode.CountryCode

expect class DatabaseFactory {
    fun create(): AppDatabase
}

interface AppDatabase {

    companion object {

        fun create(driver: SqlDriver) : AppDatabase = AppDatabaseImpl(driver)

    }

    suspend fun insertCountryCodes(items: List<CountryCode>)

}

internal class AppDatabaseImpl(
    private val driver: SqlDriver
) : AppDatabase {

    private val db = Database(driver)

    init {
        Database.Schema.create(driver)
        /*driver.execute(null, """
            CREATE TABLE IF NOT EXISTS countryCode (
                id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                code INTEGER NOT NULL,
                name TEXT NOT NULL
            );
        """.trimIndent(), 0)*/
    }

    override suspend fun insertCountryCodes(items: List<CountryCode>) {
        val values = items.joinToString(",") { "(?,?)" }
        driver.execute(null, "INSERT OR REPLACE INTO countryCode (code, name) VALUES $values",
            items.size * 2) {
            var index = -1
            for (i in items.indices) {
                val item = items[i]
                bindLong(++index, item.code)
                bindString(++index, item.name)
            }
        }
    }

    init {
        /*Database.Schema.migrate(
            driver = driver,
            oldVersion = 0,
            newVersion = Database.Schema.version,
            AfterVersion(1) { d ->
                d.execute(null, """
                    CREATE TABLE IF NOT EXISTS test (
                        id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                        title TEXT NOT NULL
                    );
                """.trimIndent(), 0)
            }
        )*/
        //db.testQueries.deleteAll()
        /*for (i in 0..100L) {
            db.testQueries.insertOrReplaceRecipe(id = i + 1, title = "title-$i")
        }
        db.testQueries.getAllRecipes().executeAsList().forEach { x ->
            println(" -------- ${x.id} : ${x.title}")
        }*/
    }

}
