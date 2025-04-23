package com.digi.virtualwardrobe.wardrobe.viewModel

import com.digi.virtualwardrobe.shared.events.ViewModelEvents
import com.digi.virtualwardrobe.wardrobe.actions.WardrobeActions
import com.digi.virtualwardrobe.wardrobe.domain.repository.CreateWardrobeRepository
import com.digi.virtualwardrobe.wardrobe.domain.repository.WardrobeRepository
import com.digi.virtualwardrobe.wardrobe.state.WardrobeState

class WardrobeViewModel(
    private val repository: WardrobeRepository,
    private val createWardrobeRepository: CreateWardrobeRepository
) : ViewModelEvents<WardrobeState, WardrobeActions>(WardrobeState()) {

    init {
        println("123123123 init")

        runOnIo {
            repository.wardrobeItems.collect { wardrobeItems ->
                updateState {
                    it.copy(
                        wardrobeItems = wardrobeItems.groupBy { it.type },
                    )
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

    override fun onCleared() {
        println("+++----===")
        super.onCleared()
    }
}