package com.example.weatherapp_compose.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherapp.screens.AboutScreen.AboutScreen
import com.example.weatherapp.screens.FavoriteScreen.FavoriteScreen
import com.example.weatherapp.screens.MainScreen.MainScreen
import com.example.weatherapp.screens.MainScreen.MainViewModel
import com.example.weatherapp.screens.SearchScreen.SearchScreen
import com.example.weatherapp.screens.SettingsScreen.SettingsScreen
import com.example.weatherapp_compose.screens.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }
        composable("${WeatherScreens.MainScreen.name}/{city}", arguments = listOf(
            navArgument(name = "city") {
                type = NavType.StringType
            }
        )) { navBack ->
            navBack.arguments?.getString("city").let {
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, mainViewModel = mainViewModel, city = it)
            }
        }
        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }
        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }
        composable(WeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController)
        }
        composable(WeatherScreens.FavoritesScreen.name) {
            FavoriteScreen(navController = navController)
        }
    }
}


