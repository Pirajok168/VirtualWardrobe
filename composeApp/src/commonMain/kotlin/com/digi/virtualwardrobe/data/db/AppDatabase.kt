package com.digi.virtualwardrobe.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.digi.virtualwardrobe.data.database.WardrobeDao
import com.digi.virtualwardrobe.data.entity.WardrobeEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Database(entities = [String::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wardrobeDao(): WardrobeDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

internal const val dbFileName = "wardrobe.db"