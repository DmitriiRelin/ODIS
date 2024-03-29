package com.odis.odis.domain.entities

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class PictureOfDay(
    val date: String,
    val explanation: String,
    val hdurl: String?,
    val mediaType: String,
    val serviceVersion: String,
    val title: String,
    val url: String?,
    val inFavorite: Boolean,
) : Parcelable