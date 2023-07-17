package com.example.weatherapp.repository

import com.example.weatherapp.data.WeatherDao
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.model.UnitSystem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {
    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()
    suspend fun insertFavorite(favorite:Favorite) = weatherDao.insertFavorite(favorite = favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite = favorite)
    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite = favorite)
    suspend fun getFavById(city: String) = weatherDao.getFavById(city)

    fun getUnit(): Flow<UnitSystem> = weatherDao.getUnitSystem().map { it ?: UnitSystem("value", unit = "metric") }
    suspend fun insertUnitSystem(unitSystem:UnitSystem) = weatherDao.insertUnitSystem(unitSystem)
    suspend fun updateUnitSystem(unitSystem:UnitSystem) = weatherDao.updateUnitSystem(unitSystem)
}