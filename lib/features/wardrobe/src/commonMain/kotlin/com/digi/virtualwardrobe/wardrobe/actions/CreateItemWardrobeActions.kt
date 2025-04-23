package com.digi.virtualwardrobe.wardrobe.actions

import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeType

sealed class CreateItemWardrobeActions {

    data class ChooseWardrobeTypeBottomSheet(
        val onClose: () -> Unit,
        val onSaveType: (WardrobeType) -> Unit
    ) : CreateItemWardrobeActions()


    data object CloseBottomSheet : CreateItemWardrobeActions()
}