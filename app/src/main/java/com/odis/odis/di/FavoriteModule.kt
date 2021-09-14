package com.odis.odis.di

import android.content.Context
import androidx.room.Room
import com.odis.odis.datalayer.localdatasource.FavoriteDB
import com.odis.odis.datalayer.localdatasource.FavoriteDao
import com.odis.odis.datalayer.repository.FavoritesRepositoryImpl
import com.odis.odis.domain.FavoritesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FavoriteModule {

    private const val DB_NAME = "Favorite.db"

    @Singleton
    @Provides
    fun provideFavoriteDao(@ApplicationContext context: Context): FavoriteDao {
        val db = Room.databaseBuilder(
            context,
            FavoriteDB::class.java,
            DB_NAME
        )
            .build()
        return db.favoriteDao()
    }

    @Singleton
    @Provides
    fun provideFavoriteRepository(favoriteDao: FavoriteDao): FavoritesRepository {
        return FavoritesRepositoryImpl(favoriteDao)
    }
}