package com.app.realestatedemoapp.data.mapper

import com.app.realestatedemoapp.data.local.entity.PropertyEntity
import com.app.realestatedemoapp.data.remote.dto.PropertyDto

fun PropertyDto.toEntity() = PropertyEntity(
    id = this.id,
    title = this.title,
    price = this.price.amount,
    locality = this.address.locality,
    street = this.address.street,
    imageUrl = this.images[0].url,
    isBookmarked = false
)