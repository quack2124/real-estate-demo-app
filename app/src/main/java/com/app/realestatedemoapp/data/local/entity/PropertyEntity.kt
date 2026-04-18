package com.app.realestatedemoapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "properties")
data class PropertyEntity(
    @PrimaryKey
    private val id: Int,
    private val title: String,
    private val price: Long,
    private val locality: String,
    private val street: String,
    private val imageUrl: String,
    private val isBookmarked: Boolean
)