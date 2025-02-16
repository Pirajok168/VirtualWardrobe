package com.digi.virtualwardrobe.wardrobe.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class WardrobeItem(
    val id: Long,
    val type: WardrobeType,
    val byteArray: ByteArray?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as WardrobeItem

        if (id != other.id) return false
        if (type != other.type) return false
        if (byteArray != null) {
            if (other.byteArray == null) return false
            if (!byteArray.contentEquals(other.byteArray)) return false
        } else if (other.byteArray != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + (byteArray?.contentHashCode() ?: 0)
        return result
    }

}
