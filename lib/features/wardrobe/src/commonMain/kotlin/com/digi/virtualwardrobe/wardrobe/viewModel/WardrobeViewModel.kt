package com.digi.virtualwardrobe.wardrobe.viewModel

import com.digi.virtualwardrobe.shared.events.ViewModelEvents
import com.digi.virtualwardrobe.wardrobe.actions.WardrobeActions
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeItem
import com.digi.virtualwardrobe.wardrobe.domain.repository.CreateWardrobeRepository
import com.digi.virtualwardrobe.wardrobe.domain.repository.WardrobeRepository
import com.digi.virtualwardrobe.wardrobe.state.WardrobeState

class WardrobeViewModel(
    private val repository: WardrobeRepository,
    private val createWardrobeRepository: CreateWardrobeRepository
) : ViewModelEvents<WardrobeState, WardrobeActions>(WardrobeState.WardrobeViewState()) {

    init {
        println("123123123 init")

        runOnIo {
            repository.wardrobeItems.collect { wardrobeItems ->
                updateState {
                    when(it) {
                        is WardrobeState.WardrobeViewState -> it.copy(
                            wardrobeItems = wardrobeItems.groupBy { it.type },
                        )

                        is WardrobeState.WardrobeEditState -> it.copy(
                            wardrobeItems = wardrobeItems.groupBy { it.type },
                        )
                    }
                }
            }
        }
    }

    fun showChoosingImageUploadOptionBottomSheetCommand(
        onDecorationWardrobeItemFlow: () -> Unit
    ) {
        runOnMain {
            updateActions(
                WardrobeActions.ShowChoosingImageUploadOptionBottomSheet(
                    onUploadImage = { path ->
                        runOnMain {
                            updateActions(WardrobeActions.CloseBottomSheet)
                            createWardrobeRepository.onSetCurrentByteArray(path.readBytes())
                            onDecorationWardrobeItemFlow()
                        }
                    },
                    onMakeImage = {
                        runOnMain {
                            updateActions(WardrobeActions.CloseBottomSheet)
                        }
                    }
                )
            )
        }
    }

    fun onEditMode() {
        updateState { WardrobeState.WardrobeEditState(it.wardrobeItems) }

        runOnMain {
            updateActions(WardrobeActions.EditMode)
        }

    }

    fun onViewMode() {
        updateState { WardrobeState.WardrobeViewState(it.wardrobeItems) }
        runOnMain {
            updateActions(WardrobeActions.ViewMode)
        }
    }

    fun onChooseItem( elem: WardrobeItem) {
        val state = uiState.value as WardrobeState.WardrobeEditState
        updateState { state.copy(selectedItem = if(state.selectedItem.contains(elem)) state.selectedItem - elem else state.selectedItem + elem) }
    }

    fun onSetNewOutfit() {
        val state = uiState.value as WardrobeState.WardrobeEditState
        runOnIo {
            repository.insertOutfit(state.selectedItem)
        }
        onViewMode()
    }

    override fun onCleared() {
        println("+++----===")
        super.onCleared()
    }
}