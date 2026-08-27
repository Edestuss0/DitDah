package com.ditdah.features.settings.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ditdah.core.settings.domain.entity.Settings
import com.ditdah.features.settings.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.settings.collectAsStateWithLifecycle()
}

@Composable
private fun SettingsContent(
    settings: Settings
) {

}