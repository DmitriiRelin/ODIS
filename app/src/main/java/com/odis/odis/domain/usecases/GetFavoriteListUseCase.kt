package com.odis.odis.domain.usecases

import com.odis.odis.domain.FavoritesRepository
import com.odis.odis.domain.entities.PictureOfDay
import javax.inject.Inject

class GetFavoriteListUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository) {

    suspend operator fun invoke(): List<PictureOfDay> = favoritesRepository.getFavoriteList()

}