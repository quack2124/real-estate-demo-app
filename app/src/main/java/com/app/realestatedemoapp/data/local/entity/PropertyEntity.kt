package com.app.realestatedemoapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "properties")
data class PropertyEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val price: Long,
    val currency: String,
    val locality: String,
    val street: String,
    val imageUrl: String,
    val isBookmarked: Boolean
)