package com.app.realestatedemoapp.data.remote

import com.app.realestatedemoapp.data.remote.dto.PropertyResponse
import retrofit2.http.GET

interface ApiService {
    @GET("properties")
    suspend fun getProperties(): PropertyResponse
}