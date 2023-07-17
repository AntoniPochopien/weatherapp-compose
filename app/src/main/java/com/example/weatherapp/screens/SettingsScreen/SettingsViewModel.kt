package com.example.weatherapp.screens.SettingsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.UnitSystem
import com.example.weatherapp.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDbRepository) : ViewModel()  {
    private val _unit = MutableStateFlow(UnitSystem("value","imperial"))
    val unit = _unit.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnit().distinctUntilChanged().collect{
                _unit.value = it
            }
        }
    }

    fun insertUnitSystem(u: UnitSystem) = viewModelScope.launch {
        repository.updateUnitSystem(u)
    }

    fun updateUnitSystem(u: UnitSystem) = viewModelScope.launch {
        repository.updateUnitSystem(u)
    }

}