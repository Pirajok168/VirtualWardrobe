package com.digi.virtualwardrobe.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.digi.virtualwardrobe.presentation.navigation.authorized.AuthorizedZone
import kotlinx.serialization.Serializable


@Serializable
data object AuthorizedZoneRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootGraph() {
    val rootNavController = rememberNavController()

    NavHost(navController = rootNavController, startDestination = AuthorizedZoneRoute) {
        composable<AuthorizedZoneRoute> {
            AuthorizedZone()
        }
    }

}