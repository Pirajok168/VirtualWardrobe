package com.digi.virtualwardrobe.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



data class WardrobeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    @SerialName("name")
    val name: String
)