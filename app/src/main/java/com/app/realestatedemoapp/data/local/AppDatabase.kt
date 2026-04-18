package com.app.realestatedemoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.realestatedemoapp.data.local.dao.PropertyDao
import com.app.realestatedemoapp.data.local.entity.PropertyEntity

@Database(
    entities = [PropertyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun propertyDao(): PropertyDao
}