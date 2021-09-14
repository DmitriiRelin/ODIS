package com.odis.odis.domain.usecases

import com.odis.odis.ResponseResult
import com.odis.odis.domain.PictureRepository
import com.odis.odis.domain.entities.PictureOfDay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPictureOfDayUseCase @Inject constructor(
    private val repository: PictureRepository
) {

    operator fun invoke(date: String? = null): Flow<ResponseResult<PictureOfDay>> =
        repository.getPictureOfDay(date)

}