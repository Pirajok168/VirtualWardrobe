package com.digi.virtualwardrobe.wardrobe.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digi.virtualwardrobe.shared.viewCommand.BottomSheetViewCommand
import com.digi.virtualwardrobe.wardrobe.commands.ChoosingImageUploadOptionBottomSheetCommand
import com.digi.virtualwardrobe.wardrobe.domain.repository.CreateWardrobeRepository
import com.digi.virtualwardrobe.wardrobe.domain.repository.WardrobeRepository
import com.digi.virtualwardrobe.wardrobe.state.WardrobeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WardrobeViewModel(
    private val repository: WardrobeRepository,
    private val bottomSheetViewCommand: BottomSheetViewCommand,
    private val createWardrobeRepository: CreateWardrobeRepository
): ViewModel () {
    private val _uiState = MutableStateFlow(WardrobeState())

    init {
        println("123123123 init")
        viewModelScope.launch {
            repository.wardrobeItems.collect { wardrobeItems ->
                _uiState.update {
                    it.copy(
                        wardrobeItems = wardrobeItems.groupBy { it.type },
                    )
                }
            }
        }
    }

    val uiState: StateFlow<WardrobeState> = _uiState

    fun showChoosingImageUploadOptionBottomSheetCommand(
        onDecorationWardrobeItemFlow: () -> Unit
    )  {
        viewModelScope.launch {
            bottomSheetViewCommand.onShowBottomSheet(
                ChoosingImageUploadOptionBottomSheetCommand(
                    onUploadImage = { path ->
                        viewModelScope.launch {
                            bottomSheetViewCommand.onCloseBottomSheet()
                            createWardrobeRepository.onSetCurrentByteArray(path.readBytes())
                        }
                        onDecorationWardrobeItemFlow()
                    },
                    onMakeImage = {
                        viewModelScope.launch {
                            bottomSheetViewCommand.onCloseBottomSheet()
                        }
                    }
                )
            )
        }
    }

    override fun onCleared() {
        println("+++----===")
        super.onCleared()
    }
}