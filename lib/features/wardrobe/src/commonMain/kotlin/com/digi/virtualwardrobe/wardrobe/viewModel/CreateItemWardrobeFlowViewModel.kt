package com.digi.virtualwardrobe.wardrobe.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digi.virtualwardrobe.shared.events.ViewModelEvents
import com.digi.virtualwardrobe.wardrobe.actions.CreateItemWardrobeActions
import com.digi.virtualwardrobe.wardrobe.commands.ChooseWardrobeTypeBottomSheetCommand
import com.digi.virtualwardrobe.wardrobe.domain.repository.CreateWardrobeRepository
import com.digi.virtualwardrobe.wardrobe.state.CreateItemWardrobeFlowState
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
) : ViewModelEvents<CreateItemWardrobeFlowState, CreateItemWardrobeActions>(CreateItemWardrobeFlowState()) {


    init {
        runOnIo {
            createWardrobeRepository.currentByteArray.collect { bytes ->
                updateState {
                    it.copy(image = bytes)
                }
            }
        }


    }


    fun onSelectWardrobeCategory() {
        runOnMain {
            updateActions(CreateItemWardrobeActions.ChooseWardrobeTypeBottomSheet(
                onClose = {
                    runOnMain {
                        updateActions(CreateItemWardrobeActions.CloseBottomSheet)
                    }
                },
                onSaveType = { selectedType ->
                    runOnMain {
                        updateActions(CreateItemWardrobeActions.CloseBottomSheet)
                    }

                    updateState {
                        it.copy(
                            selectedWardrobeType = selectedType
                        )
                    }
                }
            ))
        }
    }

    fun onDeleteType() {
        updateState {
            it.copy(
                selectedWardrobeType = null
            )
        }
    }

    fun onInputDescription(value: String){
        updateState {
            it.copy(
                descriptionWardrobe = value
            )
        }
    }

    fun onCreateWardrobe(onClose: ()->Unit) {
        viewModelScope.launch {
            createWardrobeRepository.onCreateWardrobe(
                type = uiState.value.selectedWardrobeType!!,
                byteArray = uiState.value.image,
                description = uiState.value.descriptionWardrobe
            )
        }.invokeOnCompletion {
            onClose()
        }
    }
}