package com.digi.virtualwardrobe.wardrobe.commands

import com.digi.virtualwardrobe.shared.model.BottomSheetCommand
import io.github.vinceglb.filekit.core.PlatformFile

data class ChoosingImageUploadOptionBottomSheetCommand(
    val onUploadImage: (PlatformFile) -> Unit,
    val onMakeImage: () -> Unit
) : BottomSheetCommand
