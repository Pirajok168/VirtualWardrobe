package com.digi.virtualwardrobe.shared.viewCommand

import com.digi.virtualwardrobe.shared.model.BottomSheetCommand
import kotlinx.coroutines.flow.SharedFlow

interface BottomSheetViewCommand {
    val bottomSheetCommand: SharedFlow<BottomSheetCommand>

    suspend fun onShowBottomSheet(bottomSheetCommand: BottomSheetCommand)

    suspend fun onCloseBottomSheet()
}