package com.ditdah.features.symbols.play.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesomeMosaic
import androidx.compose.material.icons.filled.LocalFireDepartment
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ditdah.components.freemode.view.FreemodeTemplateScreen
import com.ditdah.core.designsystem.component.AppCard
import com.ditdah.core.designsystem.component.AppSnackbarVisuals
import com.ditdah.core.designsystem.component.LoadingScreen
import com.ditdah.core.designsystem.component.PrimaryButton
import com.ditdah.core.designsystem.component.SnackbarMessageType
import com.ditdah.features.symbols.play.viewmodel.SymbolsPlayEffect
import com.ditdah.features.symbols.play.viewmodel.SymbolsPlayEvent
import com.ditdah.features.symbols.play.viewmodel.SymbolsPlayViewModel

@Composable
fun SymbolsPlayScreen(
    viewModel: SymbolsPlayViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effects.collect {
            when (it) {
                is SymbolsPlayEffect.Back -> { onBack() }
                is SymbolsPlayEffect.Error -> {
                    snackbarHostState.showSnackbar(
                        AppSnackbarVisuals(
                            message = it.message,
                            type = SnackbarMessageType.ERROR
                        )
                    )
                }
            }
        }
    }

    when {
        state.isLoading -> {
            LoadingScreen()
        }
        state.question == null -> {
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Не удалось получить вопрос",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(16.dp))
                PrimaryButton(
                    text = "Повторить попытку",
                    onClick = {viewModel.onEvent(SymbolsPlayEvent.Retry)}
                )
            }
        }
        else -> {
            FreemodeTemplateScreen(
                question = state.question!!,
                answeredState = com.ditdah.components.freemode.view.AnsweredState(isAnswered = state.answeredState.isAnswered, isCorrect = state.answeredState.isCorrect),
                input = state.currentInput,
                onInput = { viewModel.onEvent(SymbolsPlayEvent.Input(it)) },
                onBack = { viewModel.onEvent(SymbolsPlayEvent.Back) },
                onAnswer = { viewModel.onEvent(SymbolsPlayEvent.Answer) },
                onContinue = { viewModel.onEvent(SymbolsPlayEvent.Continue) },
                snackbarHostState = snackbarHostState,
                title = "Повторение символов"
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AppCard(modifier = Modifier
                        .weight(1f)) {
                        Row(Modifier
                            .fillMaxWidth()
                            .padding(16.dp)) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesomeMosaic,
                                contentDescription = "symbol",
                            )
                            Text(
                                text = "Буква ${viewModel.symbol}",
                                style = MaterialTheme.typography.titleMedium
                            )
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
