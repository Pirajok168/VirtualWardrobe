package com.digi.virtualwardrobe.wardrobe.navigation

import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeItem
import kotlinx.serialization.Serializable


@Serializable
data object WardrobeNavGraph

@Serializable
data object WardrobeScreen

@Serializable
data class WardrobeDetail(val wardrobeItem: String)
