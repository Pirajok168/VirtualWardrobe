package com.digi.virtualwardrobe.wardrobe.state

import androidx.compose.runtime.Stable
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeType


@Stable
data class CreateItemWardrobeFlowState(
    val image: ByteArray? = null,
    val selectedWardrobeType: WardrobeType? = null,
    val descriptionWardrobe: String? = null
) {

    fun isReadyCreate() =
        image != null && selectedWardrobeType != null

}