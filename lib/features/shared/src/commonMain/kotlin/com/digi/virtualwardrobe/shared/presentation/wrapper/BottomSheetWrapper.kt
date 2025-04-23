package com.digi.virtualwardrobe.shared.presentation.wrapper

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetWrapper(
    isShow: Boolean,
    bottomSheetContent: @Composable ColumnScope.() -> Unit,
    onClose: () -> Unit
) {

    val scope = rememberCoroutineScope()

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val skipPartiallyExpanded by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)

    LaunchedEffect(isShow) {

        if (isShow) {
            openBottomSheet = isShow
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
                    onClose()
                }
            },
            sheetState = bottomSheetState,
            dragHandle = null,
            modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
            containerColor = MaterialTheme.colorScheme.background,
            content = bottomSheetContent
        )
    }
}

//@Composable
//private fun Content(bottomSheetCommand: BottomSheetCommand) {
//    when(bottomSheetCommand) {
//        is ChoosingImageUploadOptionBottomSheetCommand -> {
//            ChoosingImageUploadOptionBottomSheetBody(bottomSheetCommand)
//        }
//
//        is ChooseWardrobeTypeBottomSheetCommand -> {
//            ChooseWardrobeTypeBottomSheet(bottomSheetCommand)
//        }
//    }
//
//}