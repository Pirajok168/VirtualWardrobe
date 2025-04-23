package com.digi.virtualwardrobe

import com.digi.virtualwardrobe.data.db.OutfitDao
import com.digi.virtualwardrobe.data.db.OutfitDaoImpl
import com.digi.virtualwardrobe.data.db.OutfitWardrobeDao
import com.digi.virtualwardrobe.data.db.OutfitWardrobeDaoImpl
import com.digi.virtualwardrobe.data.db.WardrobeDao
import com.digi.virtualwardrobe.data.db.WardrobeDaoImpl
import org.koin.dsl.module

val WardrobeDataModule = module {
    single<WardrobeDao> { WardrobeDaoImpl(get()) }


    single<OutfitWardrobeDao> { OutfitWardrobeDaoImpl(get()) }

    single<OutfitDao> { OutfitDaoImpl(get()) }
}