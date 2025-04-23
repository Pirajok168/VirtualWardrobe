package com.digi.virtualwardrobe.wardrobe.presentation.bottomSheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.digi.virtualwardrobe.wardrobe.actions.CreateItemWardrobeActions
import com.digi.virtualwardrobe.wardrobe.commands.ChooseWardrobeTypeBottomSheetCommand
import com.digi.virtualwardrobe.wardrobe.presentation.components.WardrobeTypeItem
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeType
import com.digi.virtualwardrobe.wardrobe.utill.toName
import org.jetbrains.compose.resources.stringResource
import virtualwardrobe.lib.features.wardrobe.generated.resources.Res
import virtualwardrobe.lib.features.wardrobe.generated.resources.choose_type_wardrobe
import virtualwardrobe.lib.features.wardrobe.generated.resources.save

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseWardrobeTypeBottomSheet(
    actions: CreateItemWardrobeActions.ChooseWardrobeTypeBottomSheet
) {
    val wardrobeTypes: List<WardrobeType> = remember {
        WardrobeType.entries
    }
    var selectedWardrobeType: WardrobeType? by remember { 
        mutableStateOf(null)
    }

    Column {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    stringResource(Res.string.choose_type_wardrobe),
                    style = MaterialTheme.typography.labelMedium
                )
            },
            actions = {
                IconButton(
                    onClick = {
                        actions.onClose()
                    },
                    content = {
                        Icon(
                            Icons.Outlined.Close,
                            contentDescription = null
                        )
                    }
                )
            }
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(wardrobeTypes) {
                WardrobeTypeItem(
                    type = it,
                    selected = it == selectedWardrobeType,
                    onClick = {
                        selectedWardrobeType = it
                    },
                    size = 90.dp
                )
            }
        }
        AnimatedVisibility(selectedWardrobeType != null) {
            Button(
                onClick = {
                    actions.onSaveType(selectedWardrobeType!!)
                },
                content = {
                    Text(stringResource(Res.string.save))
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}


