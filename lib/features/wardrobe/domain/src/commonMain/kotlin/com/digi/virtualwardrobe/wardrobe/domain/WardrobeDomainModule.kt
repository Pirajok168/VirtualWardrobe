package com.digi.virtualwardrobe.wardrobe.domain

import com.digi.virtualwardrobe.WardrobeDataModule
import com.digi.virtualwardrobe.wardrobe.domain.repository.CreateWardrobeRepository
import com.digi.virtualwardrobe.wardrobe.domain.repository.CreateWardrobeRepositoryImpl
import com.digi.virtualwardrobe.wardrobe.domain.repository.WardrobeRepository
import com.digi.virtualwardrobe.wardrobe.domain.repository.WardrobeRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val WardrobeDomainModule = module {
    includes(WardrobeDataModule)

    single<WardrobeRepository> {  WardrobeRepositoryImpl(get(), get(), get()) }

    single<CreateWardrobeRepository> {  CreateWardrobeRepositoryImpl(get()) }
}