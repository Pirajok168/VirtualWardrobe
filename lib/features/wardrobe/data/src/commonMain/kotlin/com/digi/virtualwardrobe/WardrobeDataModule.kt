package com.digi.virtualwardrobe

import com.digi.virtualwardrobe.data.db.WardrobeDao
import com.digi.virtualwardrobe.data.db.WardrobeDaoImpl
import org.koin.dsl.module

val WardrobeDataModule = module {
    single<WardrobeDao> { WardrobeDaoImpl(get()) }
}