package com.app.realestatedemoapp.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class PropertyModel(
    val id: Long,
    val title: String,
    val price: Long,
    val locality: String,
    val street: String,
    val imageUrl: String,
    val isBookmarked: Boolean,
    val currency: String
) {
    fun getFullAddress() = if (street.isNotEmpty()) {
        "$street, $locality"
    } else {
        locality
    }

    fun getFormattedPrice() = "$price $currency"
}