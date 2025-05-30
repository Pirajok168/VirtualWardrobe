package com.digi.virtualwardrobe.wardrobe.presentation.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.digi.virtualwardrobe.wardrobe.domain.models.ReadyOutfit
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeItem
import com.digi.virtualwardrobe.wardrobe.viewModel.DetailWardrobeViewModel
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalSharedTransitionApi::class)
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

    state.wardrobeItem?.let {
        Content(
            wardrobeItem = it,
            onBack = onBack,
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = animatedVisibilityScope,
            outfitsList = state.outfitsList
        )
    }


}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun Content(
    wardrobeItem: WardrobeItem,
    onBack: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    outfitsList: List<ReadyOutfit>,
) {
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal),
    ) {
        Column(
            modifier = Modifier.padding(it).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            with(sharedTransitionScope) {
                ElevatedCard(
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 8.dp
                    ),
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                    modifier = Modifier
                        .sharedBounds(
                            rememberSharedContentState(key = "surface-${wardrobeItem.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(32.dp)
                            .skipToLookaheadSize(),
                        tonalElevation = 8.dp,
                        onClick = onBack,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        wardrobeItem.byteArray?.let {
                            Image(
                                it.decodeToImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier
                                    .sharedElement(
                                        sharedContentState = rememberSharedContentState(key = "image-${wardrobeItem.id}"),
                                        animatedVisibilityScope = animatedVisibilityScope
                                    )
                            )
                        }
                    }
                }
            }

            wardrobeItem.description?.let {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp),
                    tonalElevation = 8.dp,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            LazyRow {
                items(outfitsList, key = { it.id }) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .padding(top = 8.dp),
                            tonalElevation = 8.dp,
                            shape = RoundedCornerShape(16.dp),
                        ) {
                            Box(Modifier.size(100.dp))
                        }

                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                }
            }
        }
    }
}