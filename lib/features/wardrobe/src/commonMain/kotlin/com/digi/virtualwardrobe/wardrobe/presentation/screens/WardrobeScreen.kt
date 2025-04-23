package com.digi.virtualwardrobe.wardrobe.presentation.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.digi.virtualwardrobe.shared.presentation.wrapper.BottomSheetWrapper
import com.digi.virtualwardrobe.wardrobe.actions.WardrobeActions
import com.digi.virtualwardrobe.wardrobe.presentation.bottomSheet.ChoosingImageUploadOptionBottomSheetBody
import com.digi.virtualwardrobe.wardrobe.viewModel.WardrobeViewModel
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun MainContent(
    onShowDetails: (Long) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onDecorationWardrobeItemFlow: () -> Unit
) {
    val viewModel = koinViewModel<WardrobeViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val actions by viewModel.actions.collectAsStateWithLifecycle(null)
    var isShowBottomSheet by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(actions) {
        when(actions) {
            is WardrobeActions.ShowChoosingImageUploadOptionBottomSheet -> {
                isShowBottomSheet = true
            }
            WardrobeActions.CloseBottomSheet ->  {
                isShowBottomSheet = false
            }
            null -> {}
        }
    }

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

                items(list, key = { it.id }) { elem ->
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        with(sharedTransitionScope) {
                            Surface(
                                modifier = Modifier
                                    .size(180.dp)
                                    .sharedBounds(
                                        rememberSharedContentState(key = "surface-${elem.id}"),
                                        animatedVisibilityScope = animatedVisibilityScope
                                    )
                                ,
                                tonalElevation = 10.dp,
                                onClick = { onShowDetails(elem.id) }
                            ) {
                                elem.byteArray?.let {
                                    Image(
                                        bitmap = it.decodeToImageBitmap(),
                                        contentDescription = null,
                                        modifier = Modifier.sharedElement(
                                            rememberSharedContentState(key = "image-${elem.id}"),
                                            animatedVisibilityScope = animatedVisibilityScope
                                        )
                                    )
                                }
                            }
                        }

                    }
                }
            }
        }
    }


    BottomSheetWrapper(
        isShow = isShowBottomSheet,
        bottomSheetContent = {
            when(val action = actions) {
                is WardrobeActions.ShowChoosingImageUploadOptionBottomSheet ->
                    ChoosingImageUploadOptionBottomSheetBody(
                        action = action
                    )
                null, WardrobeActions.CloseBottomSheet  -> Unit
            }

        },
        onClose = {
            isShowBottomSheet = false
        }
    )
}


