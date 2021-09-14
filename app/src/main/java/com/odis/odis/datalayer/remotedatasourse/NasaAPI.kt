package com.odis.odis.datalayer.remotedatasourse

import com.odis.odis.datalayer.remotedatasourse.dto.PictureFromApi
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaAPI {

    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(
        @Query(QUERY_PARAM_DATE) date: String?,
        @Query(QUERY_PARAM_API_KEY) apiKey: String
    ): PictureFromApi


    @GET("planetary/apod")
    suspend fun getListPicturesOfTheDay(
        @Query(QUERY_PARAM_COUNT) count: Int = 10,
        @Query(QUERY_PARAM_THUMBS) thumbs: Boolean = false,
        @Query(QUERY_PARAM_API_KEY) apiKey: String
    ): List<PictureFromApi>


    companion object {
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_COUNT = "count"
        private const val QUERY_PARAM_THUMBS = "thumbs"
        private const val QUERY_PARAM_DATE = "date"
    }
}