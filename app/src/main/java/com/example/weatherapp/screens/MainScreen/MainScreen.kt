package com.example.weatherapp.screens.MainScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherItem
import com.example.weatherapp.utils.formatDate
import com.example.weatherapp.utils.formatDecimals
import com.example.weatherapp.widgets.CustomAppBar
import com.example.weatherapp.screens.MainScreen.widgets.HumidityWindPressureRow
import com.example.weatherapp.screens.MainScreen.widgets.WeatherDetailRow
import com.example.weatherapp.widgets.WeatherStateImage
import com.example.weatherapp_compose.navigation.WeatherScreens

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel(), city:String?) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData(city ?: "Seattle")
    }.value
    if (weatherData.loading == true) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

    } else if (weatherData.data != null) {
        MainScaffold(weatherData.data!!, navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.list[0].weather[0].icon}@2x.png"
    Scaffold(
        topBar = {
            CustomAppBar(
                title = weather.city.name + ", ${weather.city.country}",
                navController = navController,
                icon = null,
                isMainScreen = true,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                }
            )
        }
    )
    {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(4.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = formatDate(weather.list[0].dt),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(6.dp)
            )
            Surface(
                modifier = Modifier
                    .padding(4.dp)
                    .size(200.dp), shape = CircleShape, color = Color(0xFFFFC400)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WeatherStateImage(imageUrl = imageUrl)
                    Text(
                        text = formatDecimals(weather.list[0].temp.day) + "°",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(text = weather.list[0].weather[0].main, fontStyle = FontStyle.Italic)
                }
            }
            HumidityWindPressureRow(weather.list[0])
            itemList(weather)
        }
    }
}

@Composable
fun itemList(weather: Weather) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFEEF1EF),
        shape = RoundedCornerShape(size = 14.dp)
    ) {
        LazyColumn(modifier = Modifier.padding(2.dp), contentPadding = PaddingValues(1.dp)) {

            items(items = weather.list) { item: WeatherItem ->
                WeatherDetailRow(item)
            }
        }
    }
}

