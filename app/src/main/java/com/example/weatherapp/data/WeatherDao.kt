package com.example.weatherapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.model.UnitSystem
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * from fav_tbl")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT * from fav_tbl where city =:city")
    suspend fun getFavById(city: String): Favorite?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite:Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query("DELETE from fav_tbl")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("SELECT * from settings_tbl")
    fun getUnitSystem(): Flow<UnitSystem?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnitSystem(unitSystem: UnitSystem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnitSystem(unitSystem: UnitSystem)
}
