package com.digi.virtualwardrobe.wardrobe.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digi.virtualwardrobe.shared.viewCommand.BottomSheetViewCommand
import com.digi.virtualwardrobe.wardrobe.commands.ChoosingImageUploadOptionBottomSheetCommand
import com.digi.virtualwardrobe.wardrobe.domain.repository.CreateWardrobeRepository
import com.digi.virtualwardrobe.wardrobe.domain.repository.WardrobeRepository
import com.digi.virtualwardrobe.wardrobe.state.WardrobeState
import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WardrobeViewModel(
    private val repository: WardrobeRepository,
    private val bottomSheetViewCommand: BottomSheetViewCommand,
    private val createWardrobeRepository: CreateWardrobeRepository
): ViewModel () {
    private val _uiState = MutableStateFlow(WardrobeState())

    val uiState: StateFlow<WardrobeState> = combine(
        _uiState,
        repository.wardrobeItems
    ) { state, wardrobeItems ->
        state.copy(
            wardrobeItems = wardrobeItems.groupBy { it.type },
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = WardrobeState()
    )

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
}