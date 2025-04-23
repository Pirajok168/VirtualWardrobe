package com.digi.virtualwardrobe.wardrobe.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digi.virtualwardrobe.shared.events.ViewModelEvents
import com.digi.virtualwardrobe.wardrobe.domain.repository.WardrobeRepository
import com.digi.virtualwardrobe.wardrobe.state.DetailWardrobeState
import com.digi.virtualwardrobe.wardrobe.state.WardrobeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi

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