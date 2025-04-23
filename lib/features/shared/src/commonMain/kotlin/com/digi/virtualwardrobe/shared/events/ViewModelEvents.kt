package com.digi.virtualwardrobe.shared.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class ViewModelEvents<State, Actions>(initState: State) : ViewModel() {
    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initState)
    private val _actions: MutableSharedFlow<Actions> = MutableSharedFlow()
    val uiState: StateFlow<State> = _uiState
    val actions: SharedFlow<Actions> = _actions


    fun updateState(onUpdate: (State) -> State) {
        runOnMain {
            _uiState.update { onUpdate(it); }
        }
    }

    suspend fun updateActions(actions: Actions) {
        _actions.emit(actions)
    }

    fun runOnMain(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            block()
        }
    }

    fun runOnIo(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            block()
        }
    }


}