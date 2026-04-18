package com.app.realestatedemoapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PropertyDto(
    val id: Int,
    val title: String,
    @SerializedName("prices")
    val price: Price,
    val address: Address,
    @SerializedName("attachments")
    val images: List<Image>
)

data class Price(
    val amount: Long
)

data class Address(
    val locality: String,
    val street: String
)

data class Image(
    val url: String
)