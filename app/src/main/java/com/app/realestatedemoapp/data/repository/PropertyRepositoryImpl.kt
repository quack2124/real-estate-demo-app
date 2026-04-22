package com.app.realestatedemoapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.app.realestatedemoapp.data.local.dao.PropertyDao
import com.app.realestatedemoapp.data.mapper.toDomain
import com.app.realestatedemoapp.data.mapper.toEntity
import com.app.realestatedemoapp.data.remote.ApiService
import com.app.realestatedemoapp.domain.PropertyRepository
import com.app.realestatedemoapp.domain.model.PropertyModel
import com.app.realestatedemoapp.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PropertyRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val propertyDao: PropertyDao,
) : PropertyRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getProperties(): Flow<PagingData<PropertyModel>> {
        val result = Pager(
            config = PagingConfig(pageSize = Constants.SIZE, prefetchDistance = 5),
            pagingSourceFactory = { propertyDao.getAllProperties() }).flow
            .map { pagingData -> pagingData.map { it.toDomain() } }

        return result

    }

    override fun getBookmarkedProperties(): Flow<PagingData<PropertyModel>> {
        val result = Pager(
            config = PagingConfig(pageSize = Constants.SIZE, prefetchDistance = 5),
            pagingSourceFactory = { propertyDao.getBookmarkedPropertiesPaginated() }).flow
            .map { pagingData -> pagingData.map { it.toDomain() } }

        return result
    }

    override suspend fun refreshProperties(): Result<Unit> {
        return try {
            val properties = apiService.getProperties()
            val bookmarkedIds = propertyDao.getBookmarkedProperties().map { it.id }.toSet()
            propertyDao.deleteAllProperties()

            val newEntitiesWithBookmarksPreserved = properties.results.map {
                it.listing.toEntity().copy(isBookmarked = bookmarkedIds.contains(it.listing.id))
            }

            propertyDao.insertAllProperties(newEntitiesWithBookmarksPreserved)
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun updatePropertyBookmark(
        propertyId: Long, isBookmarked: Boolean
    ): Result<Unit> {
        return try {
            propertyDao.updatePropertyBookmark(propertyId, isBookmarked)
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}
