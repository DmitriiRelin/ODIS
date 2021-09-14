package com.odis.odis.domain

import com.odis.odis.ResponseResult
import com.odis.odis.domain.entities.PictureOfDay
import kotlinx.coroutines.flow.Flow

interface PictureRepository {

    fun getPictureOfDay(date: String? = null): Flow<ResponseResult<PictureOfDay>>

    fun getListPicturesFlow(): Flow<ResponseResult<List<PictureOfDay>>>

    suspend fun getListPictures(): List<PictureOfDay>

}