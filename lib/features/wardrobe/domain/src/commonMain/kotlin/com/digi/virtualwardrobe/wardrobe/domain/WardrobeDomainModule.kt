package com.digi.virtualwardrobe.wardrobe.domain

import com.digi.virtualwardrobe.WardrobeDataModule
import com.digi.virtualwardrobe.wardrobe.domain.repository.WardrobeRepository
import com.digi.virtualwardrobe.wardrobe.domain.repository.WardrobeRepositoryImpl
import org.koin.dsl.module

val WardrobeDomainModule = module {
    includes(WardrobeDataModule)
    single<WardrobeRepository> { WardrobeRepositoryImpl(get()) }
}