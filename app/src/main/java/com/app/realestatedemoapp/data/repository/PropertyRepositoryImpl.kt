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
    private val apiService: ApiService, private val propertyDao: PropertyDao
) : PropertyRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getProperties(): Flow<PagingData<PropertyModel>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.SIZE, prefetchDistance = 5),
            pagingSourceFactory = { propertyDao.getAllProperties() }).flow.map { pagingData -> pagingData.map { it.toDomain() } }
    }

    override suspend fun refreshProperties() {
        propertyDao.deleteAllProperties()
        val properties = apiService.getProperties()
        val entities = properties.results.map {
            it.listing.toEntity()
        }
        propertyDao.insertAllProperties(entities)
    }
}

