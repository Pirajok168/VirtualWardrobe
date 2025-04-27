package com.digi.virtualwardrobe.wardrobe.state

import androidx.compose.runtime.Stable
import com.digi.virtualwardrobe.wardrobe.domain.models.ReadyOutfit
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeItem


@Stable
data class DetailWardrobeState(
    val outfitsList: List<ReadyOutfit>,
    val wardrobeItem: WardrobeItem?
)
