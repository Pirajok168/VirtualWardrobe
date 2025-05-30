package com.digi.virtualwardrobe.presentation.navigation.authorized

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.digi.virtualwardrobe.presentation.navigation.authorized.authorized_route.MainContent
import com.digi.virtualwardrobe.presentation.navigation.authorized.tab_route.Outfits
import com.digi.virtualwardrobe.presentation.navigation.authorized.tab_route.ShoppingCart
import com.digi.virtualwardrobe.presentation.navigation.authorized.tab_route.shared.TopLevelRoute
import com.digi.virtualwardrobe.shared.manage_nav_bar.LocalManageNavBar
import com.digi.virtualwardrobe.shared.manage_nav_bar.ManageNavBar
import com.digi.virtualwardrobe.wardrobe.presentation.navigation.CreateItemWardrobeFlow
import com.digi.virtualwardrobe.wardrobe.presentation.navigation.WardrobeNavGraph
import com.digi.virtualwardrobe.wardrobe.presentation.navigation.WardrobeScreenNavHost
import com.digi.virtualwardrobe.wardrobe.presentation.screens.CreateItemWardrobeFlowScreen


fun NavController.onNavigateCreateItemWardrobeFlow() =
    navigate(CreateItemWardrobeFlow) {
        popUpTo<MainContent> {
            saveState = true
        }
        launchSingleTop = true
    }

@Composable
fun AuthorizedZone() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = MainContent) {
        composable<MainContent> {
            Content(
                onDecorationWardrobeItemFlow = navController::onNavigateCreateItemWardrobeFlow
            )
        }

        composable<CreateItemWardrobeFlow> {
            CreateItemWardrobeFlowScreen(onClose = navController::popBackStack)
        }
    }

}


@Composable
private fun Content(
    onDecorationWardrobeItemFlow: () -> Unit
) {
    val navController = rememberNavController()
    val manageNavBar = ManageNavBar()
    val isShow by manageNavBar.isShow.collectAsStateWithLifecycle()

    val topLevelRoutes = remember {
        listOf(
            TopLevelRoute("Гардероб", WardrobeNavGraph, Icons.Filled.Lock),
            TopLevelRoute("Готовые образы", Outfits, Icons.Outlined.Menu),
            TopLevelRoute("Чочется образы", ShoppingCart, Icons.Outlined.ShoppingCart)
        )
    }


    CompositionLocalProvider(LocalManageNavBar provides manageNavBar) {
        Scaffold(
            contentWindowInsets = WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal),
            bottomBar = {
                AnimatedVisibility(
                    visible = isShow,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    NavigationBar {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        topLevelRoutes.forEach { topLevelRoute ->
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        topLevelRoute.icon,
                                        contentDescription = topLevelRoute.name
                                    )
                                },
                                label = { Text(topLevelRoute.name) },
                                selected = topLevelRoute.route::class.qualifiedName == currentDestination?.route,
                                onClick = {
                                    navController.navigate(topLevelRoute.route) {
                                        // Pop up to the start destination of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        // Avoid multiple copies of the same destination when
                                        // reselecting the same item
                                        launchSingleTop = true
                                        // Restore state when reselecting a previously selected item
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController,
                startDestination = WardrobeNavGraph,
                Modifier.padding(innerPadding)
            ) {
                composable<WardrobeNavGraph> {
                    WardrobeScreenNavHost(onDecorationWardrobeItemFlow = onDecorationWardrobeItemFlow)
                }
                composable<Outfits> { Text("444") }
                composable<ShoppingCart> { Text("111") }
            }
        }

    }
}


