package com.digi.virtualwardrobe.wardrobe.state

import androidx.compose.runtime.Stable
import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeType


@Stable
data class CreateItemWardrobeFlowState(
    val image: ByteArray? = null,
    val selectedWardrobeType: WardrobeType? = null,
    val descriptionWardrobe: String? = null
) {

    fun isReadyCreate() =
        image != null && selectedWardrobeType != null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as CreateItemWardrobeFlowState

        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false
        if (selectedWardrobeType != other.selectedWardrobeType) return false
        if (descriptionWardrobe != other.descriptionWardrobe) return false

        return true
    }

    override fun hashCode(): Int {
        var result = image?.contentHashCode() ?: 0
        result = 31 * result + (selectedWardrobeType?.hashCode() ?: 0)
        result = 31 * result + (descriptionWardrobe?.hashCode() ?: 0)
        return result
    }

}