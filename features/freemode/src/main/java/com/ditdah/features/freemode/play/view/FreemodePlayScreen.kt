package com.ditdah.features.freemode.play.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.SignalCellularAlt1Bar
import androidx.compose.material.icons.filled.SignalCellularAlt2Bar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ditdah.components.freemode.view.FreemodeTemplateScreen
import com.ditdah.core.designsystem.component.AppCard
import com.ditdah.core.designsystem.component.ButtonLabel
import com.ditdah.core.designsystem.component.EmptyState
import com.ditdah.core.designsystem.component.LoadingScreen
import com.ditdah.core.morse.domain.entity.FreemodeDifficulty
import com.ditdah.features.freemode.play.viewmodel.FreemodePlayEffect
import com.ditdah.features.freemode.play.viewmodel.FreemodePlayEvent
import com.ditdah.features.freemode.play.viewmodel.FreemodePlayViewModel

@Composable
fun FreemodePlayScreen(
    viewModel: FreemodePlayViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effects.collect {
            when (it) {
                is FreemodePlayEffect.Back -> { onBack() }
            }
        }
    }

    when {
        state.isLoading -> {
            LoadingScreen()
        }
        state.question == null -> {
            EmptyState("Не удалось получить вопрос")
        }
        else -> {
            FreemodeTemplateScreen(
                question = state.question!!,
                answeredState = com.ditdah.components.freemode.view.AnsweredState(isAnswered = state.answeredState.isAnswered, isCorrect = state.answeredState.isCorrect),
                input = state.currentInput,
                onInput = {viewModel.onEvent(FreemodePlayEvent.Input(it))},
                onBack = {viewModel.onEvent(FreemodePlayEvent.Back)},
                onAnswer = {viewModel.onEvent(FreemodePlayEvent.Answer)},
                onContinue = {viewModel.onEvent(FreemodePlayEvent.Continue)},
                snackbarHostState = remember { SnackbarHostState() },
                title = "Свободный режим"
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AppCard(modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = {viewModel.onEvent(FreemodePlayEvent.ChangeModalVisibility)})) {
                        Row(Modifier
                            .fillMaxWidth()
                            .padding(16.dp)) {
                            Icon(
                                imageVector = when (state.difficulty) {
                                    FreemodeDifficulty.EASY -> Icons.Default.SignalCellularAlt1Bar
                                    FreemodeDifficulty.MEDIUM -> Icons.Default.SignalCellularAlt2Bar
                                    FreemodeDifficulty.HARD -> Icons.Default.SignalCellularAlt
                                },
                                contentDescription = "difficulty",
                            )
                            Text(
                                text = state.difficulty.label,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        DropdownMenu(
                            expanded = state.isModalMenuOpen,
                            onDismissRequest = {viewModel.onEvent(FreemodePlayEvent.ChangeModalVisibility)},
                            shape = MaterialTheme.shapes.large
                        ) {
                            FreemodeDifficulty.entries.forEach {
                                DropdownMenuItem(
                                    text = { ButtonLabel(it.label) },
                                    onClick = {
                                        viewModel.onEvent(FreemodePlayEvent.ChangeDifficulty(it))
                                        viewModel.onEvent(FreemodePlayEvent.ChangeModalVisibility)
                                    }
                                )
                            }
                        }
                    }
                    Spacer(Modifier.width(16.dp))
                    AppCard(modifier = Modifier.weight(1f)) {
                        Row(Modifier
                            .fillMaxWidth()
                            .padding(16.dp)) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = "streak",
                            )
                            Text(
                                text = "Серия - ${state.answerStreak}",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }

            }
        }
    }
}
