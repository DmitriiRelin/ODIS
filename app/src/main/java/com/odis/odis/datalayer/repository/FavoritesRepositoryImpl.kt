package com.odis.odis.datalayer.repository

import com.odis.odis.datalayer.localdatasource.FavoriteDao
import com.odis.odis.datalayer.localdatasource.dto.FavoritePicture
import com.odis.odis.domain.FavoritesRepository
import com.odis.odis.domain.entities.PictureOfDay
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoritesRepository {

    override suspend fun addToFavorite(picture: PictureOfDay) {
        favoriteDao.insert(convertPictureToFavorites(picture))
    }

    override suspend fun removeFromFavorite(picture: PictureOfDay) {
        favoriteDao.delete(convertPictureToFavorites(picture))
    }

    override suspend fun getFavoriteList(): List<PictureOfDay> {
        return favoriteDao.allPictures().map { favoritePicture ->
            PictureOfDay(
                date = favoritePicture.date,
                explanation = favoritePicture.explanation,
                hdurl = favoritePicture.hdurl,
                mediaType = favoritePicture.mediaType,
                serviceVersion = favoritePicture.serviceVersion,
                title = favoritePicture.title,
                url = favoritePicture.url,
                inFavorite = true
            )
        }
    }


    private fun convertPictureToFavorites(picture: PictureOfDay): FavoritePicture {
        return FavoritePicture(
            picture.date,
            picture.explanation,
            picture.hdurl,
            picture.mediaType,
            picture.serviceVersion,
            picture.title,
            picture.url
        )
    }
}