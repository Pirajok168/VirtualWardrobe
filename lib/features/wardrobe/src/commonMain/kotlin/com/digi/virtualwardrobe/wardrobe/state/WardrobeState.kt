package com.digi.virtualwardrobe.wardrobe.state

import androidx.compose.runtime.Stable
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeItem

@Stable
data class WardrobeState(
    val wardrobeItems: List<WardrobeItem> = emptyList()
)
