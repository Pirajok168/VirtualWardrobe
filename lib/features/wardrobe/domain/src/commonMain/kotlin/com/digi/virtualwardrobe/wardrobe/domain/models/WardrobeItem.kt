package com.digi.virtualwardrobe.wardrobe.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class WardrobeItem(
    val id: Long,
    val type: WardrobeType
)
