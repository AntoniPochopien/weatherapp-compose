package com.example.weatherapp.repository

import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherObject
import com.example.weatherapp.network.WeatherAPI
import retrofit2.http.Query
import java.lang.Exception
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherAPI) {
    suspend fun getWeather(cityQuery: String, unitSystem: String): DataOrException<Weather, Boolean, Exception> {
        val response = try{
            api.getWeather(query = cityQuery, units = unitSystem)
        }catch (e: Exception){
            return DataOrException(e=e)
        }
        return DataOrException(data = response)
    }
}