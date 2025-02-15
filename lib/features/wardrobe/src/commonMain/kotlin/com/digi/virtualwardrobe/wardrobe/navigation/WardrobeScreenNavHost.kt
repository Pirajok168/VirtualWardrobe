package com.digi.virtualwardrobe.wardrobe.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.digi.virtualwardrobe.wardrobe.screens.DetailsWardrobeScreen
import com.digi.virtualwardrobe.wardrobe.screens.MainContent
import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun WardrobeScreenNavHost(
    onDecorationWardrobeItemFlow: () -> Unit
) {
    val navController = rememberNavController()
    SharedTransitionLayout {
        NavHost(navController, startDestination = WardrobeScreen) {
            composable<WardrobeScreen> {
                MainContent(
                    onShowDetails = {
                        navController.navigate(WardrobeDetail(Json { }.encodeToString(it)))
                    },
                    animatedVisibilityScope = this@composable,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    onDecorationWardrobeItemFlow = onDecorationWardrobeItemFlow
                )
            }


            composable<WardrobeDetail> { backStackEntry ->

                DetailsWardrobeScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    animatedVisibilityScope = this@composable,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    wardrobeItem = Json { }.decodeFromString(backStackEntry.toRoute<WardrobeDetail>().wardrobeItem)
                )
            }
        }
    }

}

