package com.app.realestatedemoapp.data.remote

import com.app.realestatedemoapp.data.remote.dto.PropertyResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ApiService {
    @GET
    fun getProperties(): Flow<PropertyResponse>
}