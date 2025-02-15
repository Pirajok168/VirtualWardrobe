package com.digi.virtualwardrobe.shared.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digi.virtualwardrobe.shared.model.NoCommand
import com.digi.virtualwardrobe.shared.state.BottomSheetState
import com.digi.virtualwardrobe.shared.viewCommand.BottomSheetViewCommand
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class BottomSheetViewModel(
    private val bottomSheetCommandRepositoryImpl: BottomSheetViewCommand
) : ViewModel() {

    private val _uiState = MutableStateFlow(BottomSheetState())
    val uiState: StateFlow<BottomSheetState> = bottomSheetCommandRepositoryImpl.bottomSheetCommand
        .map {
            _uiState.value.copy(
                bottomSheetCommand = it
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BottomSheetState()
        )


    suspend fun onClose() {
        bottomSheetCommandRepositoryImpl.onCloseBottomSheet()
    }
}