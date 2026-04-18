package com.app.realestatedemoapp.data.remote.dto

import com.google.gson.annotations.SerializedName


data class LocalizationDto(
    @SerializedName("de") val locale: Locale
)

data class Locale(
    @SerializedName("attachments") val attachments: List<AttachmentDto>,
    @SerializedName("text") val title: TitleDto
)

data class AttachmentDto(
    val url: String
)

data class TitleDto(
    @SerializedName("title") val text: String
)