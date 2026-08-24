package com.ditdah.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val route: String) {
    data object Auth : Screens("auth")
    data object MainPage : Screens("main_page")
    data object FreemodePlay: Screens("freemode_play")
    data object SymbolsPlay : Screens("symbols_play/{symbol}") {
        fun navigate(symbol: String) = "symbols_play/$symbol"
    }
}

enum class Tabs(val route: String, val title: String, val icon: ImageVector) {
    Practice("practice", "Тренировка", Icons.Default.FitnessCenter),
    SymbolsHome("symbols_home", "Символы", Icons.Default.Dashboard),
    Profile("profile", "Профиль", Icons.Default.Person),
}