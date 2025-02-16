package com.digi.virtualwardrobe.wardrobe.commands

import androidx.compose.runtime.Stable
import com.digi.virtualwardrobe.shared.model.BottomSheetCommand
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeType

@Stable
data class ChooseWardrobeTypeBottomSheetCommand(
    val onClose: () -> Unit,
    val onSaveType: (WardrobeType) -> Unit
): BottomSheetCommand