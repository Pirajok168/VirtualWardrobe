package com.digi.virtualwardrobe.wardrobe.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.digi.virtualwardrobe.wardrobe.presentation.screens.DetailsWardrobeScreen
import com.digi.virtualwardrobe.wardrobe.presentation.screens.MainContent
import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
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
                        navController.navigate(WardrobeDetail(it))
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
                    wardrobeItemId = backStackEntry.toRoute<WardrobeDetail>().wardrobeItem
                )
            }
        }
    }

}

