package com.ditdah.app.navigation.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ditdah.app.navigation.Screens
import com.ditdah.features.freemode.play.view.FreemodePlayScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.MainPage.route
    ) {
        composable(Screens.MainPage.route) {
            MainPage(navController)
        }

        composable(
            route = Screens.FreemodePlay.route,
            arguments = listOf(
                navArgument("difficulty") { type = NavType.StringType }
            )
        ) {
            FreemodePlayScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}