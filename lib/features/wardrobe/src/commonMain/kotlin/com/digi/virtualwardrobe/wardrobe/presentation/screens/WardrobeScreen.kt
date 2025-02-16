package com.digi.virtualwardrobe.wardrobe.presentation.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.digi.virtualwardrobe.wardrobe.WardrobeModule
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeItem
import com.digi.virtualwardrobe.wardrobe.domain.repository.WardrobeRepository
import com.digi.virtualwardrobe.wardrobe.viewModel.WardrobeViewModel
import io.github.vinceglb.filekit.core.PlatformFile
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalResourceApi::class)
@Composable
internal fun MainContent(
    onShowDetails: (Long) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onDecorationWardrobeItemFlow: () -> Unit
) {
    val viewModel = koinViewModel<WardrobeViewModel>()
    val state by viewModel.uiState.collectAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.showChoosingImageUploadOptionBottomSheetCommand(
                        onDecorationWardrobeItemFlow = onDecorationWardrobeItemFlow,
                    )
                },
                content = {
                    Icon(Icons.Outlined.Add, contentDescription = null)
                }
            )
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(180.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = it,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            state.wardrobeItems.forEach { (type, list) ->

                item(span = { GridItemSpan(this.maxLineSpan) }, content = {
                    Text(
                        type.toString(),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                })

                items(list, key = { it.id }) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        with(sharedTransitionScope) {
                            Surface(
                                modifier = Modifier.size(180.dp)
                                    .sharedElement(
                                        rememberSharedContentState(key = "image-${it.id}"),
                                        animatedVisibilityScope = animatedVisibilityScope
                                    ),
                                tonalElevation = 10.dp,
                                onClick = { onShowDetails(it.id) }
                            ) {
                                it.byteArray?.let {
                                    Image(it.decodeToImageBitmap(), contentDescription = null)
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}


