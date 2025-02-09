package com.digi.virtualwardrobe.data.db

import org.koin.core.module.Module
import org.koin.dsl.module

actual fun dbCoreModule(): Module = module {
    single<AppDatabase> { getDatabase() }
}