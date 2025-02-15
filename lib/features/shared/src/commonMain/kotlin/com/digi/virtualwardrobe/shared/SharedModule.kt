package com.digi.virtualwardrobe.shared

import com.digi.virtualwardrobe.shared.viewCommand.BottomSheetViewCommand
import com.digi.virtualwardrobe.shared.viewCommand.BottomSheetViewCommandImpl
import com.digi.virtualwardrobe.shared.viewModel.BottomSheetViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val SharedModule = module {
    single<BottomSheetViewCommand> { BottomSheetViewCommandImpl() }
    viewModelOf(::BottomSheetViewModel)
}