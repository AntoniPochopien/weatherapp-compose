package com.example.weatherapp.widgets

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun WeatherStateImage(imageUrl:String){
    AsyncImage(model = imageUrl, contentDescription = "state image", modifier = Modifier.size(80.dp) )
}