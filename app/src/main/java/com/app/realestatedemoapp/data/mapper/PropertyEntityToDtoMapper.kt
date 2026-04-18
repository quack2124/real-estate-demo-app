package com.app.realestatedemoapp.data.mapper

import com.app.realestatedemoapp.data.local.entity.PropertyEntity
import com.app.realestatedemoapp.domain.model.PropertyModel

fun PropertyEntity.toDomain(): PropertyModel {
    return PropertyModel(
        id = this.id,
        title = this.title,
        price = this.price,
        locality = this.locality,
        street = this.street,
        imageUrl = this.imageUrl,
        isBookmarked = this.isBookmarked
    )
}