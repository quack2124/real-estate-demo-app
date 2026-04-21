package com.app.realestatedemoapp.data.mapper

import com.app.realestatedemoapp.data.local.entity.PropertyEntity
import com.app.realestatedemoapp.data.remote.dto.PropertyDto

fun PropertyDto.toEntity() = PropertyEntity(
    id = this.id,
    title = this.localization.locale.title.text,
    price = this.priceDetails.priceDto.amount,
    locality = this.address.locality,
    street = this.address.street ?: "",
    imageUrl = this.localization.locale.attachments.firstOrNull()?.url.orEmpty(),
    isBookmarked = false,
    currency = this.priceDetails.currency
)