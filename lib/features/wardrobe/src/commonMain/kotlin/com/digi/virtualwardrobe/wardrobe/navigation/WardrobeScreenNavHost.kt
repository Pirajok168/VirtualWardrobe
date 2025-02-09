package com.digi.virtualwardrobe.wardrobe.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.digi.virtualwardrobe.wardrobe.screens.WardrobeScreen

@Composable
fun WardrobeScreenNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = WardrobeScreen) {
        composable<WardrobeScreen> {
            WardrobeScreen()
        }
    }
}

