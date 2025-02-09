package com.digi.virtualwardrobe.data.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import app.cash.sqldelight.driver.native.wrapConnection
import co.touchlab.sqliter.DatabaseConfiguration
import com.digi.virtualwardrobe.WardrobeDatabase

actual class DriverFactory {

    private val dbConfig = DatabaseConfiguration(
        name = dbName,
        version = 1,
        create = { connection ->
            wrapConnection(connection) {
                WardrobeDatabase.Schema.create(it)
            }
        },
        extendedConfig = DatabaseConfiguration.Extended(foreignKeyConstraints = true)
    )

    actual fun createDriver(): SqlDriver =
        NativeSqliteDriver(dbConfig)
}