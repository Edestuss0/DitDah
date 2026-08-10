package com.ditdah.app.navigation.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ditdah.app.navigation.Screens
import com.ditdah.app.navigation.Tabs
import com.ditdah.core.designsystem.component.AppScaffold
import com.ditdah.features.freemode.home.view.FreemodeHomeScreen


@Composable
fun MainPage(
    rootNavController: NavController,
) {
    val tabNavController = rememberNavController()
    val backStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    AppScaffold(
        bottomBar = {
            NavigationBar() {
                Tabs.entries.forEach { screen ->
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) },
                        label = {Text(screen.title)},
                        onClick = {
                            tabNavController.navigate(
                                screen.route
                            ) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(
                                    tabNavController
                                        .graph
                                        .startDestinationId
                                ) {
                                    saveState = true
                                }
                            }
                        },
                    )
                }
            }
        }
    ) { contentPadding ->
        NavHost(
            modifier = Modifier.fillMaxSize().padding(bottom = contentPadding.calculateBottomPadding()),
            navController = tabNavController,
            startDestination = Tabs.FreemodeHome.route
        ) {
            composable(Tabs.FreemodeHome.route) {
                FreemodeHomeScreen(
                    onPlay = { rootNavController.navigate(Screens.FreemodePlay.navigate(it)) }
                )
            }
        }
    }
}