package com.digi.virtualwardrobe.wardrobe.state

import com.digi.virtualwardrobe.wardrobe.domain.models.Outfit

data class DetailWardrobeState(
    val outfitsList: List<Outfit>
)
