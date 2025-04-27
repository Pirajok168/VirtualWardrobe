package com.digi.virtualwardrobe.outfits.domain

import com.digi.virtualwardrobe.outfits.OutfitsDataModule
import com.digi.virtualwardrobe.outfits.domain.repository.OutfitsRepository
import com.digi.virtualwardrobe.outfits.domain.repository.OutfitsRepositoryImpl
import org.koin.dsl.module

val OutfitsDomainModule = module {
    includes(OutfitsDataModule)

    single<OutfitsRepository> { OutfitsRepositoryImpl(get()) }
}