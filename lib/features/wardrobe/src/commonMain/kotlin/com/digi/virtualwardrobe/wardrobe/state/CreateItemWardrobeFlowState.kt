package com.digi.virtualwardrobe.wardrobe.state

import androidx.compose.runtime.Stable


@Stable
data class CreateItemWardrobeFlowState(
    val image: ByteArray? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as CreateItemWardrobeFlowState

        return image.contentEquals(other.image)
    }

    override fun hashCode(): Int {
        return image.contentHashCode()
    }
}