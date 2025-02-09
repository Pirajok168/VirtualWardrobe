package com.digi.virtualwardrobe

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform