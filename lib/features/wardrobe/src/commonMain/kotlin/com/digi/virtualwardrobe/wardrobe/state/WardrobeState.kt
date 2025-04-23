package com.digi.virtualwardrobe.wardrobe.state

import androidx.compose.runtime.Stable
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeItem
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeType

@Stable
sealed class WardrobeState {
    open val wardrobeItems: Map<WardrobeType, List<WardrobeItem>> = emptyMap()

    data class WardrobeViewState(override val wardrobeItems: Map<WardrobeType, List<WardrobeItem>> = emptyMap()) :
        WardrobeState()


    data class WardrobeEditState(
        override val wardrobeItems: Map<WardrobeType, List<WardrobeItem>>,
        val selectedItem: List<WardrobeItem> = emptyList()
    ) : WardrobeState()


}
