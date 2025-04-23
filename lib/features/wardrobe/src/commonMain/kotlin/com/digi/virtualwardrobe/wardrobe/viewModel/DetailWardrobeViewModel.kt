package com.digi.virtualwardrobe.wardrobe.viewModel

import com.digi.virtualwardrobe.shared.events.ViewModelEvents
import com.digi.virtualwardrobe.wardrobe.domain.repository.WardrobeRepository
import com.digi.virtualwardrobe.wardrobe.state.DetailWardrobeState

class DetailWardrobeViewModel(
    private val idWardrobeItem: Long,
    private val repository: WardrobeRepository
) : ViewModelEvents<DetailWardrobeState, Unit>(DetailWardrobeState(emptyList(), null)) {


    init {
        runOnIo {
            val item = repository.getWardrobe(idWardrobeItem)
            updateState {
                it.copy(
                    wardrobeItem = item
                )
            }


            repository.selectOutfitsByWardrobeId(idWardrobeItem).collect { outfits ->
                updateState {
                    it.copy(
                        outfitsList = outfits
                    )
                }
            }
        }
    }
}