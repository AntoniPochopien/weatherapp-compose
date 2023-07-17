package com.example.weatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.model.UnitSystem

@Database(entities = [Favorite::class, UnitSystem::class], version = 3, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}