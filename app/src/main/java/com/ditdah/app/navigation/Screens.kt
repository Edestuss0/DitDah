package com.ditdah.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.ditdah.core.morse.model.FreemodeDifficulty

sealed class Screens(val route: String) {
    data object Auth : Screens("auth")
    data object MainPage : Screens("main_page")
    data object FreemodePlay: Screens("freemode_play")
}

enum class Tabs(val route: String, val title: String, val icon: ImageVector) {
    Practice("practice", "Тренировка", Icons.Default.FitnessCenter),
    Profile("profile", "Профиль", Icons.Default.Person)
}