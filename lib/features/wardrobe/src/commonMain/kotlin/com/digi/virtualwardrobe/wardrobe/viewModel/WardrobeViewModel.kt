package com.digi.virtualwardrobe.wardrobe.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digi.virtualwardrobe.wardrobe.domain.repository.WardrobeRepository
import com.digi.virtualwardrobe.wardrobe.state.WardrobeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WardrobeViewModel(
    private val repository: WardrobeRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(WardrobeState())
    val uiState: StateFlow<WardrobeState> = repository.wardrobeItems.map {
        _uiState.value.copy(
            wardrobeItems = it.groupBy { it.type },
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = WardrobeState()
    )

    fun add()  {
        viewModelScope.launch {
            repository.addWardrobeElem()
        }
    }

}