package com.digi.virtualwardrobe.presentation.wrapper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.digi.virtualwardrobe.shared.model.BottomSheetCommand
import com.digi.virtualwardrobe.shared.model.NoCommand
import com.digi.virtualwardrobe.shared.viewModel.BottomSheetViewModel
import com.digi.virtualwardrobe.wardrobe.commands.ChoosingImageUploadOptionBottomSheetCommand
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetWrapper() {

    val viewModel = koinViewModel<BottomSheetViewModel>()
    val state by viewModel.uiState.collectAsState()

    val scope = rememberCoroutineScope()

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var skipPartiallyExpanded by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)

    LaunchedEffect(state.bottomSheetCommand) {
        if (state.bottomSheetCommand !is NoCommand) {
            openBottomSheet = true
        } else {
            openBottomSheet = false
            scope.launch {
                bottomSheetState.hide()
            }
        }
    }

    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    viewModel.onClose()
                }
            },
            sheetState = bottomSheetState,
        ) {
            Content(state.bottomSheetCommand)
        }
    }
}

@Composable
private fun Content(bottomSheetCommand: BottomSheetCommand) {
    when(bottomSheetCommand) {
        is ChoosingImageUploadOptionBottomSheetCommand -> {
            Text("123123213")
        }
    }

}