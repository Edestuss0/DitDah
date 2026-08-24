package com.ditdah.features.symbols.play.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesomeMosaic
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ditdah.components.audioinput.view.MorseAudioInput
import com.ditdah.components.morsekey.view.MorseKey
import com.ditdah.core.designsystem.component.AppCard
import com.ditdah.core.designsystem.component.AppScaffold
import com.ditdah.core.designsystem.component.AppSnackbarHost
import com.ditdah.core.designsystem.component.AppSnackbarVisuals
import com.ditdah.core.designsystem.component.AppTextField
import com.ditdah.core.designsystem.component.LoadingScreen
import com.ditdah.core.designsystem.component.PrimaryButton
import com.ditdah.core.designsystem.component.SnackbarMessageType
import com.ditdah.core.morse.domain.entity.MorseQuestion
import com.ditdah.core.morse.domain.entity.MorseQuestionType
import com.ditdah.features.symbols.play.viewmodel.AnsweredState
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
            SymbolsPlayContent(
                question = state.question!!,
                answeredState = state.answeredState,
                input = state.currentInput,
                onInput = { viewModel.onEvent(SymbolsPlayEvent.Input(it)) },
                onBack = { viewModel.onEvent(SymbolsPlayEvent.Back) },
                onAnswer = { viewModel.onEvent(SymbolsPlayEvent.Answer) },
                onContinue = { viewModel.onEvent(SymbolsPlayEvent.Continue) },
                streak = state.answerStreak,
                snackbarHostState = snackbarHostState,
                symbol = viewModel.symbol
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SymbolsPlayContent(
    question: MorseQuestion,
    answeredState: AnsweredState,
    input: String,
    onInput: (String) -> Unit,
    onBack: () -> Unit,
    onAnswer: () -> Unit,
    onContinue: () -> Unit,
    streak: Int,
    snackbarHostState: SnackbarHostState,
    symbol: String
) {
    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        onBackClick = onBack,
        hasBackButton = true,
        topBarText = "Повторение букв",
        snackbarHost = { AppSnackbarHost(snackbarHostState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
                            text = "Буква $symbol",
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
                            text = "Серия - ${streak}",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            when (question.type) {
                MorseQuestionType.TEXT -> {
                    AppCard(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = "Введите:",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = question.question,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                    MorseKey(onChange = onInput)
                }
                MorseQuestionType.AUDIO -> {
                    MorseAudioInput(
                        text = question.question,
                        onValueChange = onInput
                    )
                }
                MorseQuestionType.MORSE -> {
                    AppCard(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Переведите - ${question.question}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            AppTextField(
                                value = input,
                                onValueChange = onInput,
                                label = "Ваш ответ",

                                )
                        }
                    }
                }
            }
            Spacer(Modifier.weight(1f))
            PrimaryButton(
                text = "Проверить",
                onClick = onAnswer
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = if (answeredState.isAnswered) 0.5f else 0.0f))
        ) {
            AnimatedVisibility(
                visible = answeredState.isAnswered,
                enter = slideInVertically(initialOffsetY = {it}),
                exit = slideOutVertically(targetOffsetY = { it }),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraLarge.copy(bottomEnd = CornerSize(0.dp), bottomStart = CornerSize(0.dp)),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column() {
                            Text(
                                text = if (answeredState.isCorrect) "Правильно!" else "Увы, неправильно",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = "Правильный ответ - ${question.answer}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Spacer(Modifier.height(16.dp))
                        PrimaryButton(
                            text = "Дальше",
                            onClick = onContinue,
                            colors = ButtonColors(
                                containerColor = if (answeredState.isCorrect) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.errorContainer,
                                contentColor = if (answeredState.isCorrect) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onErrorContainer,
                                disabledContainerColor = Color.White,
                                disabledContentColor = Color.Black
                            )
                        )
                    }
                }
            }
        }
    }
}