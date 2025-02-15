package com.digi.virtualwardrobe

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import com.digi.virtualwardrobe.data.db.dbCoreModule
import com.digi.virtualwardrobe.data.di.dbModule
import com.digi.virtualwardrobe.presentation.root.AppContent
import com.digi.virtualwardrobe.wardrobe.WardrobeModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.KoinIsolatedContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import org.koin.dsl.koinApplication
import org.koin.dsl.module

object MyIsolatedKoinContext {
    private var _koinApp: KoinApplication? = null

    fun getOrCreateKoinApp(appDeclaration: KoinAppDeclaration = {}): KoinApplication {
        return _koinApp ?: koinApplication {
            appDeclaration()
            modules(dbCoreModule(), dbModule, WardrobeModule)
        }.also { _koinApp = it }
    }
}

@Composable
@Preview
fun App(appDeclaration: KoinAppDeclaration = {}) {
    MaterialTheme {
        KoinIsolatedContext(
            context = MyIsolatedKoinContext.getOrCreateKoinApp(appDeclaration)
        ) {
            AppContent()
        }
    }
}
