package com.digi.virtualwardrobe.data.di

import com.digi.virtualwardrobe.data.database.WardrobeDao
import com.digi.virtualwardrobe.data.db.AppDatabase
import org.koin.dsl.module

val dbModule = module {
    single<WardrobeDao> { get<AppDatabase>().wardrobeDao() }
}