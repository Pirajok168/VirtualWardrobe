package com.digi.virtualwardrobe.wardrobe.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeType
import com.digi.virtualwardrobe.wardrobe.utill.toName
import org.jetbrains.compose.resources.stringResource

@Composable
fun WardrobeTypeItem(
    type: WardrobeType,
    selected: Boolean,
    size: Dp,
    onClick: () -> Unit = {},
    onClose: (() -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(size)
        ){
            Surface(
                tonalElevation = 8.dp,
                shape = CircleShape,
                modifier = Modifier.size(size),
                onClick = onClick,
                selected = selected,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
                    .takeIf { selected }
            ) {

            }

            onClose?.let {
                Box(
                    Modifier.size(size).offset(x = 10.dp, y = -10.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    FilledIconButton(
                        onClick = it,
                        content = {
                            Icon(Icons.Outlined.Close, contentDescription = null)
                        },
                        modifier = Modifier.requiredSize(30.dp)
                    )
                }
            }
        }


        Text(
            text = stringResource(type.toName()),
            style = MaterialTheme.typography.labelMedium
        )
    }
}