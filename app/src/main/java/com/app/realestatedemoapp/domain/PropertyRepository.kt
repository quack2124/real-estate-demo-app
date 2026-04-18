package com.app.realestatedemoapp.domain

import androidx.paging.PagingData
import com.app.realestatedemoapp.domain.model.PropertyModel
import kotlinx.coroutines.flow.Flow

interface PropertyRepository {

    fun getProperties(): Flow<PagingData<PropertyModel>>
    fun getBookmarkedProperties(): Flow<PagingData<PropertyModel>>
    suspend fun refreshProperties()
    suspend fun updatePropertyBookmark(propertyId: Long, isBookmarked: Boolean)
}