package com.example.weatherapp.widgets

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.screens.FavoriteScreen.FavoriteViewModel
import com.example.weatherapp_compose.navigation.WeatherScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    title: String?,
    icon: ImageVector = Icons.Default.ArrowBack,
    isMainScreen: Boolean = false,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val showDropdownMenu = remember {
        mutableStateOf(false)
    }

    if (showDropdownMenu.value) {
        ShowSettingDropDownMenu(showDropdownMenu = showDropdownMenu, navController = navController)
    }

    CenterAlignedTopAppBar(
        title = {
            if (title != null) Text(
                text = title!!,

                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
            )
        },

        actions = {
            if (isMainScreen) {
                IconButton(onClick = { onAddActionClicked.invoke() }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")
                }
                IconButton(onClick = { showDropdownMenu.value = !showDropdownMenu.value }) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "Dropdown menu icon"
                    )
                }
            }
        },
        navigationIcon = {
            if (isMainScreen) {
                val splitTitle = title!!.split(", ")
                val isFavorite = favoriteViewModel.favList.collectAsState().value.any { item ->
                    (item.city == title.split(", ")[0])
                }
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    tint = Color.Red,
                    contentDescription = "",
                    modifier = Modifier
                        .scale(0.9f)
                        .clickable {
                            if (isFavorite) {
                                favoriteViewModel.deleteFavorite(
                                    Favorite(
                                        city = splitTitle[0],
                                        country = splitTitle[1]
                                    )
                                )
                            } else {
                                favoriteViewModel.insertFavorite(
                                    Favorite(
                                        city = splitTitle[0],
                                        country = splitTitle[1]
                                    )
                                )
                            }

                        }
                )
            } else {
                Icon(
                    imageVector = icon!!,
                    contentDescription = "Navigation icon",
                    modifier = Modifier.clickable { onButtonClicked.invoke() }, tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background,
            actionIconContentColor = MaterialTheme.colorScheme.background
        ),

        )


}

@Composable
fun ShowSettingDropDownMenu(showDropdownMenu: MutableState<Boolean>, navController: NavController) {
    var expanded = remember {
        mutableStateOf(true)
    }
    val items = listOf("Favorites", "About", "Settings")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = {
                expanded.value = false
                showDropdownMenu.value = false
            },
            modifier = Modifier
                .width(140.dp)
                .background(color = Color.White)
        ) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(text = { Text(text = text) }, onClick = {
                    expanded.value = false
                    showDropdownMenu.value = false
                    navController.navigate(
                        when (text) {
                            "About" -> WeatherScreens.AboutScreen.name
                            "Settings" -> WeatherScreens.SettingsScreen.name
                            "Favorites" -> WeatherScreens.FavoritesScreen.name
                            else -> WeatherScreens.SettingsScreen.name
                        }
                    )
                },
                    leadingIcon = {
                        Icon(
                            imageVector = when (text) {
                                "About" -> Icons.Default.Info
                                "Favorites" -> Icons.Default.FavoriteBorder
                                else -> Icons.Default.Settings
                            }, contentDescription = "Item icon"
                        )
                    }

                )
            }
        }
    }
}
