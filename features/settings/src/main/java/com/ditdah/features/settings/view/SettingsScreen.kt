package com.ditdah.features.settings.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.FitnessCenter
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ditdah.core.designsystem.component.AppCard
import com.ditdah.core.designsystem.component.AppScaffold
import com.ditdah.core.designsystem.component.LoadingScreen
import com.ditdah.core.settings.domain.entity.Language
import com.ditdah.core.settings.domain.entity.Settings
import com.ditdah.features.settings.viewmodel.SettingsEvent
import com.ditdah.features.settings.viewmodel.SettingsViewModel
import kotlin.math.roundToInt

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val settings by viewModel.settings.collectAsStateWithLifecycle()

    when {
        settings == null -> {
            AppScaffold(
                hasBackButton = true,
                topBarText = "Настройки",
                onBackClick = onBack
            ) {
                LoadingScreen()
            }
        }
        else -> {
            SettingsContent(
                settings = settings!!,
                onBack = onBack,
                onThemeChange = {viewModel.onEvent(SettingsEvent.ChangeTheme)},
                onWpmChange = {viewModel.onEvent(SettingsEvent.ChangeWpm(it))},
                onCacheDaysChange = {viewModel.onEvent(SettingsEvent.ChangeCacheDuration(it))},
                onChangeLanguageModalVisibility = {viewModel.onEvent(SettingsEvent.LanguageModalChangeVisibility)},
                onLanguageChange = {viewModel.onEvent(SettingsEvent.ChangeLanguage(it))},
                isLanguageModalOpen = state.isLanguageModalOpen
            )
        }
    }

}

@Composable
private fun SettingsContent(
    settings: Settings,
    onBack: () -> Unit,
    onThemeChange: () -> Unit,
    onWpmChange: (Int) -> Unit,
    onCacheDaysChange: (Int) -> Unit,
    onChangeLanguageModalVisibility: () -> Unit,
    onLanguageChange: (Language) -> Unit,
    isLanguageModalOpen: Boolean
) {
    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        hasBackButton = true,
        topBarText = "Настройки",
        onBackClick = onBack,
        statusBarColor = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.extraLarge.copy(topEnd = CornerSize(0.dp), topStart = CornerSize(0.dp)),
                color = MaterialTheme.colorScheme.surfaceContainer
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Настройки",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Настройте обучение под себя",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Icon(
                        imageVector = Icons.TwoTone.Settings,
                        contentDescription = null,
                        modifier = Modifier.size(56.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AppCard(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp, horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column() {
                            Text(text = "Тёмная тема", style = MaterialTheme.typography.titleLarge)
                            Text(text = "Стиль оформления приложения", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Switch(checked = settings.isDarkTheme, onCheckedChange = { onThemeChange() })
                    }
                }

                AppCard(modifier = Modifier.fillMaxWidth().clickable(onClick = onChangeLanguageModalVisibility)) {
                    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp, horizontal = 16.dp)) {
                        Text(text = "Язык — ${settings.language.label}", style = MaterialTheme.typography.titleLarge)
                        Text(text = "Изменить язык обучения", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    DropdownMenu(
                        expanded = isLanguageModalOpen,
                        onDismissRequest = onChangeLanguageModalVisibility,
                        shape = MaterialTheme.shapes.large
                    ) {
                        Language.entries.forEach {
                            DropdownMenuItem(text = { Text(it.label) }, onClick = {onLanguageChange(it)})
                        }
                    }
                }

                AppCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp, horizontal = 16.dp),) {
                        Text(text = "WPM — ${settings.wpm}", style = MaterialTheme.typography.titleLarge)
                        Text(text = "Скорость сигнала Морзе", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.height(8.dp))
                        Slider(
                            value = settings.wpm.toFloat(),
                            onValueChange = { onWpmChange(it.roundToInt()) },
                            steps = 14,
                            valueRange = 5f..20f
                        )
                    }
                }

                AppCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp, horizontal = 16.dp),) {
                        Text(text = "Кеш хранится ${settings.daysToCache} дней", style = MaterialTheme.typography.titleLarge)
                        Text(text = "Измените длительность хранения кеша", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.height(8.dp))
                        Slider(
                            value = settings.daysToCache.toFloat(),
                            onValueChange = { onCacheDaysChange(it.roundToInt()) },
                            steps = 8,
                            valueRange = 1f..10f
                        )
                    }
                }
            }
        }
    }
}