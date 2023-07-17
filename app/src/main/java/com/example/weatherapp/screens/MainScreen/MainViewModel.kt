package com.example.weatherapp.screens.MainScreen

import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.Weather
import com.example.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    suspend fun getWeatherData(city: String, unitSystem: String): DataOrException<Weather, Boolean, Exception>{
        return  repository.getWeather(cityQuery = city, unitSystem = unitSystem)
    }

}