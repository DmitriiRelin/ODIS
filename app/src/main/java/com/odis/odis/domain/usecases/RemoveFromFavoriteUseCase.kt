package com.odis.odis.domain.usecases

import com.odis.odis.domain.FavoritesRepository
import com.odis.odis.domain.entities.PictureOfDay
import javax.inject.Inject

class RemoveFromFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(pictureOfDay: PictureOfDay) =
        favoritesRepository.removeFromFavorite(pictureOfDay)
}