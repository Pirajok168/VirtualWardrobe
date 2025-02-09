package com.digi.virtualwardrobe.wardrobe.domain

import com.digi.virtualwardrobe.wardrobe.domain.repository.WardrobeRepository
import com.digi.virtualwardrobe.wardrobe.domain.repository.WardrobeRepositoryImpl
import org.koin.dsl.module

val WardrobeDomainModule = module {
    single<WardrobeRepository> { WardrobeRepositoryImpl(get()) }
}