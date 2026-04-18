package com.app.realestatedemoapp.data.remote.dto

data class PropertyResponse(
    val size:Long,
    val total:Long,
    val results:List<PropertyDto>
)