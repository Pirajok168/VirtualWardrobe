package com.digi.virtualwardrobe.wardrobe.presentation.navigation

import androidx.navigation.NavController
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeItem
import kotlinx.serialization.Serializable


@Serializable
data object WardrobeNavGraph

@Serializable
data object WardrobeScreen

@Serializable
data class WardrobeDetail(val wardrobeItem: Long)

@Serializable
data object CreateItemWardrobeFlow
