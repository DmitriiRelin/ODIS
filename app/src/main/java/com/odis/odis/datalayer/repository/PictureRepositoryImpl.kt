package com.odis.odis.datalayer.repository

import com.odis.odis.ResponseResult
import com.odis.odis.datalayer.localdatasource.FavoriteDao
import com.odis.odis.datalayer.remotedatasourse.RemoteDataSource
import com.odis.odis.datalayer.remotedatasourse.dto.PictureFromApi
import com.odis.odis.domain.PictureRepository
import com.odis.odis.domain.entities.PictureOfDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PictureRepositoryImpl @Inject constructor(
    private val dataSource: RemoteDataSource,
    private val dao: FavoriteDao
) : PictureRepository {

    override fun getPictureOfDay(date: String?): Flow<ResponseResult<PictureOfDay>> = flow {
        emit(ResponseResult.Loading())
        try {
            val pictureFromApi = dataSource.getPictureOfDay(date)
            emit(ResponseResult.Success(pictureFromApiConvert(pictureFromApi, date)))
        } catch (e: Exception) {
            emit(ResponseResult.Error(e))
        }

    }

    override fun getListPicturesFlow(): Flow<ResponseResult<List<PictureOfDay>>> = flow {
        emit(ResponseResult.Loading())
        try {
            val list = dataSource.getListPictures()
                .map { picture -> pictureFromApiConvert(pictureFromApi = picture, picture.date) }
            emit(ResponseResult.Success(list))
        } catch (e: Exception) {
            emit(ResponseResult.Error(e))
        }
    }

    override suspend fun getListPictures(): List<PictureOfDay> {
        return dataSource.getListPictures()
            .map { picture -> pictureFromApiConvert(pictureFromApi = picture, picture.date) }
    }


    private suspend fun pictureFromApiConvert(
        pictureFromApi: PictureFromApi,
        date: String?
    ): PictureOfDay =
        PictureOfDay(
            date = pictureFromApi.date,
            explanation = pictureFromApi.explanation,
            hdurl = pictureFromApi.hdurl,
            mediaType = pictureFromApi.mediaType,
            serviceVersion = pictureFromApi.serviceVersion,
            title = pictureFromApi.title,
            url = pictureFromApi.url,
            inFavorite = if (date == null)
                dao.isTodayPictureFavorite()
            else
                dao.isPictureFavorite(date)
        )

}