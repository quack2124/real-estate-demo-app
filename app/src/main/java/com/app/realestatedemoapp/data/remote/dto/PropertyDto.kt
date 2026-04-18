package com.app.realestatedemoapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PropertyDto(
    val id: Long,
    val localization: LocalizationDto,
    @SerializedName("prices") val priceDetails: PriceDetailsDto,
    val address: AddressDto,
)

data class PriceDetailsDto(
    @SerializedName("buy") val priceDto: PriceDto
)

data class PriceDto(
    @SerializedName("price") val amount: Long
)

data class AddressDto(
    val locality: String, val street: String?
)
