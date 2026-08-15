package com.ditdah.app.navigation.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ditdah.app.navigation.Screens
import com.ditdah.app.navigation.viewmodel.NavigationViewModel
import com.ditdah.core.designsystem.component.LoadingScreen
import com.ditdah.features.auth.view.AuthScreen
import com.ditdah.features.freemode.play.view.FreemodePlayScreen

@Composable
fun AppNavigation(viewModel: NavigationViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val authState by viewModel.authState.collectAsState()

    if (authState == null) {
        LoadingScreen()
        return
    }

    NavHost(
        navController = navController,
        startDestination = if (authState == true) Screens.MainPage.route else Screens.Auth.route
    ) {

        composable(Screens.Auth.route) {
            AuthScreen()
        }

        composable(Screens.MainPage.route) {
            MainPage(navController)
        }

        composable(
            route = Screens.FreemodePlay.route,
        ) {
            FreemodePlayScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}