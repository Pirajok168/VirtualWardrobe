package com.digi.virtualwardrobe.wardrobe.state

import androidx.compose.runtime.Stable
import com.digi.virtualwardrobe.wardrobe.domain.models.Outfit
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeItem


@Stable
data class DetailWardrobeState(
    val outfitsList: List<Outfit>,
    val wardrobeItem: WardrobeItem?
)
