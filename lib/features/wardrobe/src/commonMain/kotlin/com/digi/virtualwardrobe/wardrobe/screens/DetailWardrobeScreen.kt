package com.digi.virtualwardrobe.wardrobe.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeItem
import com.digi.virtualwardrobe.wardrobe.viewModel.DetailWardrobeViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun DetailsWardrobeScreen(
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