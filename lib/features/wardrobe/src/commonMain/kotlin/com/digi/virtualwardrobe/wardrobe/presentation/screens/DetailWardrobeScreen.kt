package com.digi.virtualwardrobe.wardrobe.presentation.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.digi.virtualwardrobe.wardrobe.viewModel.DetailWardrobeViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalResourceApi::class)
@Composable
internal fun DetailsWardrobeScreen(
    wardrobeItemId: Long,
    onBack: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    val detailViewModel: DetailWardrobeViewModel =
        koinViewModel<DetailWardrobeViewModel> { parametersOf(wardrobeItemId) }

    val state by detailViewModel.uiState.collectAsState()

    if (state.wardrobeItem == null)
        return
    Column() {
        with(sharedTransitionScope) {
            Surface(
                modifier = Modifier.size(180.dp)
                    .sharedElement(
                        rememberSharedContentState(key = "image-${state.wardrobeItem?.id}"),
                        animatedVisibilityScope = animatedVisibilityScope
                    ),
                tonalElevation = 10.dp,
                onClick = onBack
            ) {
                state.wardrobeItem?.byteArray?.let {
                    Image(
                        it.decodeToImageBitmap(),
                        contentDescription = null,
                    )
                }
            }
        }

        state.outfitsList.forEach {
            Text(it.name)

            Text(it.description.orEmpty())
        }

    }
}