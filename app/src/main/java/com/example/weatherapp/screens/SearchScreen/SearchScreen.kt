package com.example.weatherapp.screens.SearchScreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapp.widgets.CustomAppBar
import com.example.weatherapp_compose.navigation.WeatherScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    Scaffold(topBar = {
        CustomAppBar(
            title = "Search",
            icon = Icons.Default.ArrowBack,
            navController = navController,
        ) {
            navController.popBackStack()
        }
    }) {
        Surface(modifier = Modifier.padding(it)) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar(modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)) {
                    mCity->
                    navController.navigate(WeatherScreens.MainScreen.name + "/$mCity")
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier, onSearch: (String) -> Unit) {
    val searchQueryState = rememberSaveable {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(searchQueryState.value) {
        searchQueryState.value.trim().isNotEmpty()
    }
    Column() {
        CommonTextField(
            valueState = searchQueryState,
            placeHolder = "Seattle",
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch.invoke(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTextField(
    valueState: MutableState<String>,
    placeHolder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = {
            Text(
                text = placeHolder
            )

        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Blue, cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    )
}
