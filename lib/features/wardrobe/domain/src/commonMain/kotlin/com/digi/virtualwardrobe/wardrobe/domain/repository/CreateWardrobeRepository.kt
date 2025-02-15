package com.digi.virtualwardrobe.wardrobe.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CreateWardrobeRepository {
    val currentByteArray: StateFlow<ByteArray?>


    suspend fun onSetCurrentByteArray(byteArray: ByteArray)
}