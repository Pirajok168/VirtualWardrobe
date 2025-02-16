package com.digi.virtualwardrobe.wardrobe.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digi.virtualwardrobe.shared.viewCommand.BottomSheetViewCommand
import com.digi.virtualwardrobe.shared.viewModel.BottomSheetViewModel
import com.digi.virtualwardrobe.wardrobe.commands.ChooseWardrobeTypeBottomSheetCommand
import com.digi.virtualwardrobe.wardrobe.domain.repository.CreateWardrobeRepository
import com.digi.virtualwardrobe.wardrobe.state.CreateItemWardrobeFlowState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateItemWardrobeFlowViewModel(
    private val createWardrobeRepository: CreateWardrobeRepository,
    private val bottomSheetViewCommand: BottomSheetViewCommand
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(CreateItemWardrobeFlowState())

    val uiState: StateFlow<CreateItemWardrobeFlowState> = combine(
        _uiState,
        createWardrobeRepository.currentByteArray
    ) { state, byteArray ->
        state.copy(
            image = byteArray
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CreateItemWardrobeFlowState()
    )

    init {
        viewModelScope.launch {
            createWardrobeRepository.currentByteArray.collect { bytes ->
                _uiState.update {
                    it.copy(image = bytes)
                }
            }
        }
    }


    fun onSelectWardrobeCategory() {
        viewModelScope.launch {
            bottomSheetViewCommand.onShowBottomSheet(
                ChooseWardrobeTypeBottomSheetCommand(
                    onClose = {
                        viewModelScope.launch {
                            bottomSheetViewCommand.onCloseBottomSheet()
                        }
                    },
                    onSaveType = { selectedType ->
                        viewModelScope.launch {
                            bottomSheetViewCommand.onCloseBottomSheet()
                        }
                        _uiState.update {
                            it.copy(
                                selectedWardrobeType = selectedType
                            )
                        }
                    }
                )
            )
        }
    }

    fun onDeleteType() {
        _uiState.update {
            it.copy(
                selectedWardrobeType = null
            )
        }
    }

    fun onInputDescription(value: String){
        _uiState.update {
            it.copy(
                descriptionWardrobe = value
            )
        }
    }

    fun onCreateWardrobe(onClose: ()->Unit) {
        viewModelScope.launch {
            createWardrobeRepository.onCreateWardrobe(
                type = _uiState.value.selectedWardrobeType!!,
                byteArray = _uiState.value.image,
                description = uiState.value.descriptionWardrobe
            )
        }.invokeOnCompletion {
            onClose()
        }
    }
}