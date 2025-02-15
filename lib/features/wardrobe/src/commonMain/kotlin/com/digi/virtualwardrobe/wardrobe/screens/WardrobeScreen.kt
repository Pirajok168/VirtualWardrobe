package com.digi.virtualwardrobe.wardrobe.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeItem
import com.digi.virtualwardrobe.wardrobe.viewModel.DetailWardrobeViewModel
import com.digi.virtualwardrobe.wardrobe.viewModel.WardrobeViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainContent(
    onShowDetails: (WardrobeItem) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val viewModel = koinViewModel<WardrobeViewModel>()
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                content = {
                    Icon(Icons.Outlined.Add, contentDescription = null)
                }
            )
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(180.dp),
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.wardrobeItems.forEach { (type, list) ->

                item(span = { GridItemSpan(this.maxLineSpan) }, content = {
                    Text(type.toString())
                })

                items(list, key = { it.id }) {
                    Column {
                        with(sharedTransitionScope) {
                            Surface(
                                modifier = Modifier.size(180.dp)
                                    .sharedElement(
                                        rememberSharedContentState(key = "image-$it"),
                                        animatedVisibilityScope = animatedVisibilityScope
                                    ),
                                tonalElevation = 10.dp,
                                onClick = { onShowDetails(it) }
                            ) {

                            }
                        }

                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailsContent(
    wardrobeItem: WardrobeItem,
    onBack: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    val detailViewModel: DetailWardrobeViewModel =
        koinViewModel<DetailWardrobeViewModel> { parametersOf(wardrobeItem.id) }

    val state by detailViewModel.uiState.collectAsState()
    Column() {
        with(sharedTransitionScope) {
            Surface(
                modifier = Modifier.size(180.dp)
                    .sharedElement(
                        rememberSharedContentState(key = "image-$wardrobeItem"),
                        animatedVisibilityScope = animatedVisibilityScope
                    ),
                tonalElevation = 10.dp,
                onClick = onBack
            ) {

            }
        }

        state.outfitsList.forEach {
            Text(it.name)

            Text(it.description.orEmpty())
        }

    }
}