package com.app.realestatedemoapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.realestatedemoapp.data.local.entity.PropertyEntity

@Dao
interface PropertyDao {

    @Query("SELECT * FROM properties")
    fun getAllProperties(): PagingSource<Int, PropertyEntity>

    @Query("DELETE FROM properties")
    suspend fun deleteAllProperties()

    @Insert
    suspend fun insertAllProperties(propertyEntities: List<PropertyEntity>)
}