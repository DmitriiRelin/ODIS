package com.odis.odis.datalayer.localdatasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.odis.odis.datalayer.localdatasource.dto.FavoritePicture

@Database(entities = [FavoritePicture::class], version = 1, exportSchema = false)
abstract class FavoriteDB : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}