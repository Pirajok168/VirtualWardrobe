package com.digi.virtualwardrobe.wardrobe.domain.repository


import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CreateWardrobeRepository {
    val currentByteArray: StateFlow<ByteArray?>


    suspend fun onSetCurrentByteArray(byteArray: ByteArray)


    suspend fun onCreateWardrobe(type: WardrobeType, byteArray: ByteArray?, description: String?)
}