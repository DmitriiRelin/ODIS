package com.odis.odis.domain

import com.odis.odis.domain.entities.PictureOfDay

interface FavoritesRepository {

    suspend fun addToFavorite(picture: PictureOfDay)

    suspend fun removeFromFavorite(picture: PictureOfDay)

    suspend fun getFavoriteList(): List<PictureOfDay>

}