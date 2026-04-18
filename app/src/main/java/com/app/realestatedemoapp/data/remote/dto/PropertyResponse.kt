package com.app.realestatedemoapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PropertyResponse(
    val size: Long, val total: Long, @SerializedName("results") val results: List<ResultWrapperDto>
)

data class ResultWrapperDto(
    @SerializedName("listing") val listing: PropertyDto
)