package com.digi.virtualwardrobe.wardrobe.domain.models

data class Outfit(
    val id: Long,
    val name: String,
    val description: String?,
    val image: ByteArray?,
)
