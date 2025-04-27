package com.digi.virtualwardrobe.outfits

import com.digi.virtualwardrobe.outfits.data.OutfitDao
import com.digi.virtualwardrobe.outfits.data.OutfitDaoImpl
import org.koin.dsl.module

val OutfitsDataModule = module {
    single<OutfitDao> { OutfitDaoImpl(get()) }
}