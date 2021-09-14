package com.odis.odis.di

import com.odis.odis.datalayer.localdatasource.FavoriteDao
import com.odis.odis.datalayer.remotedatasourse.RemoteDataSource
import com.odis.odis.datalayer.repository.PictureRepositoryImpl
import com.odis.odis.domain.PictureRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PictureModule {

    @Singleton
    @Provides
    fun providePictureRepository(
        dataSource: RemoteDataSource,
        favoriteDao: FavoriteDao
    ): PictureRepository {
        return PictureRepositoryImpl(dataSource, favoriteDao)
    }

}