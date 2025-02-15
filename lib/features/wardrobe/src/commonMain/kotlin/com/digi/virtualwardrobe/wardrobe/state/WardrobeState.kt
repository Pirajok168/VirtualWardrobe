package com.digi.virtualwardrobe.wardrobe.state

import androidx.compose.runtime.Stable
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeItem
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeType

@Stable
data class WardrobeState(
    val wardrobeItems: Map<WardrobeType, List<WardrobeItem>> = emptyMap()
)
