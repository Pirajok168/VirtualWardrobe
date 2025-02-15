package com.digi.virtualwardrobe.wardrobe


import com.digi.virtualwardrobe.wardrobe.domain.WardrobeDomainModule
import com.digi.virtualwardrobe.wardrobe.viewModel.CreateItemWardrobeFlowViewModel
import com.digi.virtualwardrobe.wardrobe.viewModel.DetailWardrobeViewModel
import com.digi.virtualwardrobe.wardrobe.viewModel.WardrobeViewModel
import org.koin.core.module.dsl.viewModelOf

import org.koin.dsl.module

val WardrobeModule = module {
    includes(WardrobeDomainModule)
    viewModelOf(::WardrobeViewModel)
    viewModelOf(::DetailWardrobeViewModel)

    viewModelOf(::CreateItemWardrobeFlowViewModel)
}