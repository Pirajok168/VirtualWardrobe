package com.digi.virtualwardrobe.shared.state

import androidx.compose.runtime.Stable
import com.digi.virtualwardrobe.shared.model.BottomSheetCommand
import com.digi.virtualwardrobe.shared.model.NoCommand


@Stable
data class BottomSheetState(
    val bottomSheetCommand: BottomSheetCommand = NoCommand
)