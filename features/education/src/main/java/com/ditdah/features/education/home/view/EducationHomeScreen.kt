package com.ditdah.features.education.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ditdah.core.designsystem.component.AppBadge
import com.ditdah.core.designsystem.component.AppCard
import com.ditdah.core.designsystem.component.AppScaffold
import com.ditdah.core.designsystem.component.AppSnackbarHost
import com.ditdah.core.designsystem.component.AppSnackbarVisuals
import com.ditdah.core.designsystem.component.EmptyState
import com.ditdah.core.designsystem.component.LoadingScreen
import com.ditdah.core.designsystem.component.PrimaryButton
import com.ditdah.core.designsystem.component.SnackbarMessageType
import com.ditdah.core.education.domain.entity.Lesson
import com.ditdah.core.settings.domain.entity.Language
import com.ditdah.features.education.home.viewmodel.EducationEffect
import com.ditdah.features.education.home.viewmodel.EducationEvent
import com.ditdah.features.education.home.viewmodel.EducationHomeViewModel

@Composable
fun EducationHomeScreen(
    viewModel: EducationHomeViewModel = hiltViewModel(),
    onPlay: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effects.collect {
            when (it) {
                is EducationEffect.Error -> {
                    snackbarHostState.showSnackbar(
                        AppSnackbarVisuals(
                            type = SnackbarMessageType.ERROR,
                            message = it.message
                        )
                    )
                }
            }
        }
    }

    when {
        state.isLoading && state.lesson == null -> {
            LoadingScreen()
        }

        state.lesson == null || state.language == null -> {
            AppScaffold(modifier = Modifier.fillMaxSize()) {
                PullToRefreshBox(
                    isRefreshing = state.isLoading,
                    onRefresh = { viewModel.onEvent(EducationEvent.Refresh) }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        EmptyState("Не удалось получить необходимую информацию")
                        Spacer(Modifier.height(16.dp))
                        PrimaryButton(
                            text = "Повторить попытку",
                            onClick = { viewModel.onEvent(EducationEvent.Refresh) })
                    }
                }
            }
        }

        else -> {
            AppScaffold(
                modifier = Modifier.fillMaxSize(),
                snackbarHost = { AppSnackbarHost(snackbarHostState) }) {
                PullToRefreshBox(
                    isRefreshing = state.isLoading,
                    onRefresh = { viewModel.onEvent(EducationEvent.Refresh) }
                ) {
                    EducationHomeContent(
                        lesson = state.lesson!!,
                        onPlay = onPlay,
                        language = state.language!!,
                    )
                }
            }
        }
    }

}

@Composable
private fun EducationHomeContent(
    lesson: Lesson,
    onPlay: () -> Unit,
    language: Language,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AppCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                AppBadge(
                    text = "Награда — ${lesson.xpReward} опыта",
                    icon = Icons.Default.AutoAwesome
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = lesson.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = lesson.description,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        AppCard(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AppBadge(text = "Язык", icon = Icons.Default.Language)
                Text(
                    text = language.label,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        PrimaryButton(text = "Пройти урок", onClick = onPlay)
    }
}