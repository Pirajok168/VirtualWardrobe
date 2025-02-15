package com.digi.virtualwardrobe.wardrobe.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.digi.virtualwardrobe.wardrobe.viewModel.CreateItemWardrobeFlowViewModel
import com.digi.virtualwardrobe.wardrobe.viewModel.DetailWardrobeViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CreateItemWardrobeFlowScreen() {
    val createItemWardrobeFlowViewModel: CreateItemWardrobeFlowViewModel =
        koinViewModel<CreateItemWardrobeFlowViewModel>()

    val state by createItemWardrobeFlowViewModel.uiState.collectAsState()



    Scaffold {
        Column(
            modifier = Modifier.padding(it).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                tonalElevation = 8.dp,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
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
    }
}