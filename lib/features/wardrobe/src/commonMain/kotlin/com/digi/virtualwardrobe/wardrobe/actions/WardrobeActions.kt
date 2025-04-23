package com.digi.virtualwardrobe.wardrobe.actions

import io.github.vinceglb.filekit.core.PlatformFile

sealed class WardrobeActions {
    data class ShowChoosingImageUploadOptionBottomSheet(
        val onUploadImage: (PlatformFile) -> Unit,
        val onMakeImage: () -> Unit
    ) : WardrobeActions()


    data object  CloseBottomSheet : WardrobeActions()
}