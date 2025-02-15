package com.digi.virtualwardrobe.wardrobe.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digi.virtualwardrobe.shared.viewCommand.BottomSheetViewCommand
import com.digi.virtualwardrobe.wardrobe.commands.ChoosingImageUploadOptionBottomSheetCommand
import com.digi.virtualwardrobe.wardrobe.domain.repository.WardrobeRepository
import com.digi.virtualwardrobe.wardrobe.state.WardrobeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WardrobeViewModel(
    private val repository: WardrobeRepository,
    private val bottomSheetViewCommand: BottomSheetViewCommand
): ViewModel(){
    private val _uiState = MutableStateFlow(WardrobeState())
    val uiState: StateFlow<WardrobeState> = repository.wardrobeItems.map {
        _uiState.value.copy(
            wardrobeItems = it.groupBy { it.type },
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = WardrobeState()
    )

    fun showChoosingImageUploadOptionBottomSheetCommand()  {
        viewModelScope.launch {
            bottomSheetViewCommand.onShowBottomSheet(ChoosingImageUploadOptionBottomSheetCommand)
        }
    }

}