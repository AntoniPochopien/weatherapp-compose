package com.example.weatherapp.screens.SettingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.model.UnitSystem
import com.example.weatherapp.widgets.CustomAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {


    val state =settingsViewModel.unit.collectAsState()
    var unitToggleState = remember {
        mutableStateOf(true)
    }
    Scaffold(topBar = {
        CustomAppBar(title = "Settings", navController = navController) {
            navController.popBackStack()
        }
    }) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Change Units of Measurement",
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                IconToggleButton(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(
                            Color.Magenta.copy(alpha = 0.4f)
                        ),
                    checked = unitToggleState.value, onCheckedChange = { v ->
                        unitToggleState.value = v
                    }) {
                    Text(text = if (unitToggleState.value) "Farenhait" else "Celsius C)")
                }
                Button(
                    onClick = {
                              settingsViewModel.updateUnitSystem(UnitSystem("value", if (unitToggleState.value) "imperial" else "metric"))
                    },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEFBE42))
                ) {
                    Text(
                        text = "Save",
                        modifier = Modifier.padding(4.dp),
                        color = Color.White,
                        fontSize = 17.sp
                    )
                }
            }

        }
    }
}