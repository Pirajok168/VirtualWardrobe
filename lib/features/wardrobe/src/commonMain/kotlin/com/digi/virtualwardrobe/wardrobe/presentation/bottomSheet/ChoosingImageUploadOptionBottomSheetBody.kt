package com.digi.virtualwardrobe.wardrobe.presentation.bottomSheet

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.digi.virtualwardrobe.shared.model.BottomSheetCommand
import com.digi.virtualwardrobe.wardrobe.commands.ChoosingImageUploadOptionBottomSheetCommand
import com.preat.peekaboo.ui.camera.PeekabooCamera
import com.preat.peekaboo.ui.camera.rememberPeekabooCameraState
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.PickerMode
import io.github.vinceglb.filekit.core.PickerType
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import virtualwardrobe.lib.features.wardrobe.generated.resources.Res
import virtualwardrobe.lib.features.wardrobe.generated.resources.make_photo
import virtualwardrobe.lib.features.wardrobe.generated.resources.upload_photo


@Composable
fun ChoosingImageUploadOptionBottomSheetBody(
    command: ChoosingImageUploadOptionBottomSheetCommand
) {

    val launcher = rememberFilePickerLauncher(
        mode = PickerMode.Single,
        type = PickerType.Image
    ) { files ->
        files?.let {
            command.onUploadImage(it)
        }
    }

    var showCamera by remember {
        mutableStateOf(false)
    }
    val state = rememberPeekabooCameraState(onCapture = {

    })
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(16.dp)
    ) {
        ActionElem(
            name = Res.string.make_photo,
            onAction = {
                showCamera = true
            }
        )

        Spacer(Modifier.padding(8.dp))

        ActionElem(
            name = Res.string.upload_photo,
            onAction = {
                launcher.launch()
            }
        )
    }
    // FIXME: нужно тестить на реальном устройстве
    if (showCamera){
        PeekabooCamera(
            state = state,
            modifier = Modifier.fillMaxSize(),
            permissionDeniedContent = {
                // Custom UI content for permission denied scenario
            },
        )
    }
}

@Composable
private fun RowScope.ActionElem(
    name: StringResource,
    onAction: () -> Unit
) {
    Surface(
        modifier = Modifier.height(120.dp).weight(1f),
        tonalElevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        onClick = onAction
    ) {
        Column(
            modifier = Modifier.height(120.dp).padding(6.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(stringResource(name))
        }
    }
}