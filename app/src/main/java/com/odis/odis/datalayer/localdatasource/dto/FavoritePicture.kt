package com.odis.odis.datalayer.localdatasource.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoritePictures")
data class FavoritePicture(
    @PrimaryKey
    val date: String,
    val explanation: String,
    val hdurl: String?,
    val mediaType: String,
    val serviceVersion: String,
    val title: String,
    val url: String?,
)