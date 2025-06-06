package com.digi.virtualwardrobe.data.di


import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import com.digi.virtualwardrobe.WardrobeDatabase
import com.digi.virtualwardrobe.outfits.data.OutfitEntityQueries
import com.digi.virtualwardrobe.wardrobe.data.OutfitWardrobeEntityQueries
import com.digi.virtualwardrobe.wardrobe.data.WardrobeEntity
import com.digi.virtualwardrobe.wardrobe.data.WardrobeEntityQueries
import org.koin.dsl.module

val dbModule = module {
    single<WardrobeDatabase> {
        WardrobeDatabase(
            get<SqlDriver>(),
            WardrobeEntity.Adapter(
                EnumColumnAdapter()
            )
        )
    }

    single<WardrobeEntityQueries> { get<WardrobeDatabase>().wardrobeEntityQueries }

    single<OutfitWardrobeEntityQueries> { get<WardrobeDatabase>().outfitWardrobeEntityQueries }

    single<OutfitEntityQueries> { get<WardrobeDatabase>().outfitEntityQueries }
}