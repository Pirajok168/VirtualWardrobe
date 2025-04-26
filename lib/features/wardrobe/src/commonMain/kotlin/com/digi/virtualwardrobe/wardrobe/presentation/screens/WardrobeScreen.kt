package com.digi.virtualwardrobe.wardrobe.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.digi.virtualwardrobe.shared.manage_nav_bar.LocalManageNavBar
import com.digi.virtualwardrobe.shared.presentation.wrapper.BottomSheetWrapper
import com.digi.virtualwardrobe.wardrobe.actions.WardrobeActions
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeItem
import com.digi.virtualwardrobe.wardrobe.presentation.bottomSheet.ChoosingImageUploadOptionBottomSheetBody
import com.digi.virtualwardrobe.wardrobe.state.WardrobeState
import com.digi.virtualwardrobe.wardrobe.viewModel.WardrobeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    val localManageNavBar = LocalManageNavBar.current
    var isEditMode by remember {
        mutableStateOf(false)
    }


    val haptics = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()
    LaunchedEffect(actions) {

        when (actions) {
            is WardrobeActions.ShowChoosingImageUploadOptionBottomSheet -> {
                isShowBottomSheet = true
            }

            WardrobeActions.CloseBottomSheet -> {
                isShowBottomSheet = false
            }

            WardrobeActions.EditMode -> {
                isEditMode = true
            }
            WardrobeActions.ViewMode -> {
                isEditMode = false
            }

            null -> {}
        }
    }

    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(
                !isEditMode,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
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
        },
        bottomBar = {
            AnimatedVisibility(
                isEditMode,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                BottomAppBar {
                    IconButton(
                        onClick = {
                            viewModel.onViewMode()
                            localManageNavBar.showNavBar()
                        },
                        content = {
                            Icon(Icons.Outlined.Close, contentDescription = null)
                        }
                    )

                    IconButton(
                        onClick = {
                            viewModel.onSetNewOutfit()
                            localManageNavBar.showNavBar()
                        },
                        content = {
                            Icon(Icons.Outlined.Add, contentDescription = null)
                        }
                    )
                }
            }
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
                    val isElemSelected by remember(state.isElemSelected(elem)) {
                        mutableStateOf(state.isElemSelected(elem))
                    }
                    GridElem(
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                        elem = elem,
                        onLongClick = {
                            haptics.performHapticFeedback(
                                HapticFeedbackType.LongPress
                            )
                            scope.launch {
                                localManageNavBar.hideNavbar()
                                delay(300)
                                viewModel.onEditMode()
                            }
                        },
                        selected = isElemSelected,
                        onClick = {
                            when (state) {
                                is WardrobeState.WardrobeEditState ->
                                    viewModel.onChooseItem(elem)

                                is WardrobeState.WardrobeViewState ->
                                    onShowDetails(elem.id)
                            }

                        }
                    )
                }
            }
        }
    }


    BottomSheetWrapper(
        isShow = isShowBottomSheet,
        bottomSheetContent = {
            when (val action = actions) {
                is WardrobeActions.ShowChoosingImageUploadOptionBottomSheet ->
                    ChoosingImageUploadOptionBottomSheetBody(
                        action = action
                    )

                else -> Unit
            }

        },
        onClose = {
            isShowBottomSheet = false
        }
    )
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun GridElem(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    elem: WardrobeItem,
    onLongClick: () -> Unit,
    selected: Boolean,
    onClick: () -> Unit
) {
    val animatedEditView by animateDpAsState(if (selected) 18.dp else 0.dp)
    with(sharedTransitionScope) {
        Surface(
            modifier = androidx.compose.ui.Modifier
                .padding(horizontal = 16.dp)
                .size(180.dp)
                .sharedBounds(
                    rememberSharedContentState(key = "surface-${elem.id}"),
                    animatedVisibilityScope = animatedVisibilityScope
                ),
            tonalElevation = 10.dp,
            shadowElevation = animatedEditView,
        ) {
            Box {
                Image(
                    bitmap = elem.byteArray!!.decodeToImageBitmap(),
                    contentDescription = null,
                    modifier = androidx.compose.ui.Modifier.sharedElement(
                        rememberSharedContentState(key = "image-${elem.id}"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                        .combinedClickable(
                            onClick = onClick,
                            onLongClick = onLongClick,
                        ),
                )

                Box(
                    androidx.compose.ui.Modifier.matchParentSize().background(
                        if (selected) androidx.compose.ui.graphics.Color.White.copy(
                            alpha = 0.4f
                        ) else androidx.compose.ui.graphics.Color.Transparent
                    )
                )
            }
        }
    }
}

