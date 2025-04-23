package com.digi.virtualwardrobe.shared.manage_nav_bar

import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.hapticfeedback.HapticFeedback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


@Stable
class ManageNavBar {

    val isShow: MutableStateFlow<Boolean> = MutableStateFlow(true);

    fun hideNavbar() {
        isShow.update {
            false
        }
    }


    fun showNavBar() {
        isShow.update {
            true
        }
    }
}


val LocalManageNavBar =
    staticCompositionLocalOf<ManageNavBar> {  error("CompositionLocal not present") }