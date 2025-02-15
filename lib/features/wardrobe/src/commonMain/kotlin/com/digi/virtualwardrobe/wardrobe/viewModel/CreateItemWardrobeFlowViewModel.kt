package com.digi.virtualwardrobe.wardrobe.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digi.virtualwardrobe.wardrobe.domain.repository.CreateWardrobeRepository
import com.digi.virtualwardrobe.wardrobe.state.CreateItemWardrobeFlowState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class CreateItemWardrobeFlowViewModel(
    createWardrobeRepository: CreateWardrobeRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(CreateItemWardrobeFlowState())

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
}