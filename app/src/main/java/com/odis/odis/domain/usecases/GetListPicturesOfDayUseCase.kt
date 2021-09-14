package com.odis.odis.domain.usecases

import com.odis.odis.ResponseResult
import com.odis.odis.domain.PictureRepository
import com.odis.odis.domain.entities.PictureOfDay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListPicturesOfDayUseCase @Inject constructor(private val repository: PictureRepository) {

    operator fun invoke(): Flow<ResponseResult<List<PictureOfDay>>> =
        repository.getListPicturesFlow()

    suspend fun getList(): List<PictureOfDay> = repository.getListPictures()

}