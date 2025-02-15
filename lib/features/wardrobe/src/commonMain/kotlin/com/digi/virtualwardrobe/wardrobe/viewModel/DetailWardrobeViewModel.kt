package com.digi.virtualwardrobe.wardrobe.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digi.virtualwardrobe.wardrobe.domain.repository.WardrobeRepository
import com.digi.virtualwardrobe.wardrobe.state.DetailWardrobeState
import com.digi.virtualwardrobe.wardrobe.state.WardrobeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailWardrobeViewModel(
    private val idWardrobeItem: Long,
    private val repository: WardrobeRepository,
): ViewModel() {

    private val _uiState = MutableStateFlow(DetailWardrobeState(emptyList()))
    val uiState: StateFlow<DetailWardrobeState> = repository.selectOutfitsByWardrobeId(idWardrobeItem).map {
        _uiState.value.copy(
            outfitsList = it
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DetailWardrobeState(emptyList())
    )
}