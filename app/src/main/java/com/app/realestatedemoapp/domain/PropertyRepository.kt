package com.app.realestatedemoapp.domain

import androidx.paging.PagingData
import com.app.realestatedemoapp.domain.model.PropertyModel
import kotlinx.coroutines.flow.Flow

interface PropertyRepository {

    fun getProperties(): Result<Flow<PagingData<PropertyModel>>>
    fun getBookmarkedProperties(): Result<Flow<PagingData<PropertyModel>>>
    suspend fun refreshProperties(): Result<Unit>
    suspend fun updatePropertyBookmark(propertyId: Long, isBookmarked: Boolean): Result<Unit>
}