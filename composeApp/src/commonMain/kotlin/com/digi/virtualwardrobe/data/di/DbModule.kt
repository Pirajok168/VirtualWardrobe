package com.digi.virtualwardrobe.data.di


import app.cash.sqldelight.db.SqlDriver
import com.digi.virtualwardrobe.WardrobeDatabase
import com.digi.virtualwardrobe.wardrobe.data.WardrobeEntityQueries
import org.koin.dsl.module

val dbModule = module {
    single<WardrobeDatabase> { WardrobeDatabase(get<SqlDriver>())}

    single<WardrobeEntityQueries> { get<WardrobeDatabase>().wardrobeEntityQueries }
}