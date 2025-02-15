package com.digi.virtualwardrobe.shared.viewCommand


import com.digi.virtualwardrobe.shared.model.BottomSheetCommand
import com.digi.virtualwardrobe.shared.model.NoCommand
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class BottomSheetViewCommandImpl: BottomSheetViewCommand {

    private val _bottomSheetCommand =
        MutableSharedFlow<BottomSheetCommand>(replay = 0)

    override val bottomSheetCommand: SharedFlow<BottomSheetCommand>
        get() = _bottomSheetCommand.asSharedFlow()

    override suspend fun onShowBottomSheet(bottomSheetCommand: BottomSheetCommand) =
        _bottomSheetCommand.emit(bottomSheetCommand)

    override suspend fun onCloseBottomSheet() =
        _bottomSheetCommand.emit(NoCommand)


}