package com.digi.virtualwardrobe.data.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun dbCoreModule(): Module = module(createdAtStart = true) {
    single<SqlDriver> { DriverFactory(get()).createDriver() }
}