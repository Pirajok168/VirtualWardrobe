package com.digi.virtualwardrobe.data.db

import app.cash.sqldelight.db.SqlDriver

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

internal val dbName = "wardrobeDatabase.db"