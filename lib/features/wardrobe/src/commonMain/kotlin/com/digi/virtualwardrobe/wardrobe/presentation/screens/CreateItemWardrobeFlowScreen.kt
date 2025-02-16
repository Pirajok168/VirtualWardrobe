package com.digi.virtualwardrobe.wardrobe.presentation.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.digi.virtualwardrobe.wardrobe.presentation.components.WardrobeTypeItem
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeType
import com.digi.virtualwardrobe.wardrobe.viewModel.CreateItemWardrobeFlowViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import virtualwardrobe.lib.features.wardrobe.generated.resources.Res
import virtualwardrobe.lib.features.wardrobe.generated.resources.choose_type_wardrobe
import virtualwardrobe.lib.features.wardrobe.generated.resources.choose_type_wardrobe_description
import virtualwardrobe.lib.features.wardrobe.generated.resources.selected_type_wardrobe
import virtualwardrobe.lib.features.wardrobe.generated.resources.user_description_wardrobe
import virtualwardrobe.lib.features.wardrobe.generated.resources.add

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CreateItemWardrobeFlowScreen(onClose: () -> Unit) {
    val createItemWardrobeFlowViewModel: CreateItemWardrobeFlowViewModel =
        koinViewModel<CreateItemWardrobeFlowViewModel>()

    val state by createItemWardrobeFlowViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal),
        bottomBar = {
            Button(
                onClick = {
                    createItemWardrobeFlowViewModel.onCreateWardrobe(onClose)
                },
                content = {
                    Text(stringResource(Res.string.add))
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ElevatedCard(
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 8.dp
                ),
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
            ) {
                Surface(
                    tonalElevation = 8.dp,
                    modifier = Modifier.fillMaxWidth().padding(32.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    state.image?.let {
                        Image(
                            bitmap = it.decodeToImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.size(300.dp).padding(16.dp)
                        )
                    }
                }
            }


            Surface(
                tonalElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Crossfade(state.selectedWardrobeType) {
                    it?.let {
                        SelectedWardrobeType(
                            wardrobeType = it,
                            onDeleteType = createItemWardrobeFlowViewModel::onDeleteType
                        )
                    } ?: run {
                        EmptySelectedWardrobeTypeCard(
                            onClick = createItemWardrobeFlowViewModel::onSelectWardrobeCategory
                        )
                    }
                }
            }

            DescriptionWardrobeItemCard(
                descriptionText = state.descriptionWardrobe.orEmpty(),
                onInput = createItemWardrobeFlowViewModel::onInputDescription
            )
        }
    }
}


@Composable
private fun DescriptionWardrobeItemCard(
    descriptionText: String,
    onInput: (String) -> Unit
) {
    Surface(
        tonalElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp)
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp)
    ) {

        Box {
            TextField(
                value = descriptionText,
                onValueChange = onInput,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp)
                    .padding(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
            )


            Box(Modifier.matchParentSize(), contentAlignment = Alignment.Center) {
                AnimatedVisibility(
                    descriptionText.isEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            stringResource(Res.string.user_description_wardrobe),
                            style = MaterialTheme.typography.bodyMedium
                                .copy(
                                    color = MaterialTheme.colorScheme.outline,
                                    textAlign = TextAlign.Center
                                ),
                        )
                    }
                }
            }
        }

    }
}



@Composable
private fun SelectedWardrobeType(
    wardrobeType: WardrobeType,
    onDeleteType: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            stringResource(Res.string.selected_type_wardrobe),
            style = MaterialTheme.typography.titleMedium
                .copy(textAlign = TextAlign.Center),
            modifier = Modifier.fillMaxWidth()
        )

        WardrobeTypeItem(
            type = wardrobeType,
            selected = false,
            size = 70.dp,
            onClose = onDeleteType
        )
    }
}

@Composable
private fun EmptySelectedWardrobeTypeCard(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilledIconButton(
            onClick = onClick,
            content = {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = null
                )
            }
        )
        Spacer(Modifier.size(16.dp))

        Text(
            stringResource(Res.string.choose_type_wardrobe),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.size(8.dp))

        Text(
            stringResource(Res.string.choose_type_wardrobe_description),
            style = MaterialTheme.typography.bodyMedium
                .copy(
                    color = MaterialTheme.colorScheme.outline,
                    textAlign = TextAlign.Center
                ),
        )
    }

}