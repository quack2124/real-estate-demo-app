package com.app.realestatedemoapp.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class PropertyModel(
    val id: Int,
    val title: String,
    val price: Long,
    val locality: String,
    val street: String,
    val imageUrl: String,
    val isBookmarked: Boolean
)