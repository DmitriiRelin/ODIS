package com.odis.odis.datalayer.localdatasource

import androidx.room.*
import com.odis.odis.datalayer.localdatasource.dto.FavoritePicture
import java.time.LocalDate

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM FavoritePictures")
    suspend fun allPictures(): List<FavoritePicture>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoritePicture: FavoritePicture)

    @Delete
    suspend fun delete(favoritePicture: FavoritePicture)

    suspend fun isTodayPictureFavorite(): Boolean =
        allPictures().find { LocalDate.parse(it.date) == LocalDate.now() } != null

    suspend fun isPictureFavorite(date: String): Boolean =
        allPictures().find { it.date == date } != null
}