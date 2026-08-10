package com.ditdah.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.ui.graphics.vector.ImageVector
import com.ditdah.core.morse.model.FreemodeDifficulty

sealed class Screens(val route: String) {
    data object MainPage : Screens("main_page")
    data object FreemodePlay: Screens("freemode_play/{difficulty}") {
        fun navigate(difficulty: FreemodeDifficulty) = "freemode_play/$difficulty"
    }
}

enum class Tabs(val route: String, val title: String, val icon: ImageVector) {
    FreemodeHome("freemode_home", "Свободный", Icons.Default.Keyboard)
}