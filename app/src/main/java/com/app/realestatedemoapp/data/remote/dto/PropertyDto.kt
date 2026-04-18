package com.app.realestatedemoapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PropertyDto(
    private val title: String,
    @SerializedName("prices")
    private val price: Price,
    private val address: Address,
    @SerializedName("attachments")
    private val images: List<Image>
)

data class Price(
    private val amount: Long
)

data class Address(
    private val locality: String,
    private val street: String
)

data class Image(
    private val url: String
)