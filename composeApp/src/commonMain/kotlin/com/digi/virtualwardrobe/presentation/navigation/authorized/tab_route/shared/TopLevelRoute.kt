package com.digi.virtualwardrobe.presentation.navigation.authorized.tab_route.shared

import androidx.compose.ui.graphics.vector.ImageVector

// FIXME: возможно стоит перенести это потом в общий модуль
data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)
