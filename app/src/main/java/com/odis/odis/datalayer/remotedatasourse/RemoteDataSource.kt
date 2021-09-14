package com.odis.odis.datalayer.remotedatasourse

import com.odis.odis.BuildConfig
import com.odis.odis.Utils.BASE_URL
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Inject

class RemoteDataSource @Inject constructor() {

    private val nasaAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().setLenient().create())
        )
        .client(createOkHttpClient(PODInterceptor()))
        .build()
        .create(NasaAPI::class.java)

    suspend fun getPictureOfDay(date: String?) =
        nasaAPI.getPictureOfTheDay(date, apiKey = BuildConfig.nasa_api_key)

    suspend fun getListPictures() =
        nasaAPI.getListPicturesOfTheDay(apiKey = BuildConfig.nasa_api_key)


    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    inner class PODInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            return chain.proceed(chain.request())
        }
    }
}