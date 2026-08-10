package com.ditdah.features.freemode.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ditdah.core.designsystem.component.AppCard
import com.ditdah.core.designsystem.component.AppScaffold
import com.ditdah.core.designsystem.component.ButtonLabel
import com.ditdah.core.designsystem.component.PrimaryButton
import com.ditdah.core.designsystem.theme.DitdahTheme
import com.ditdah.core.morse.model.FreemodeDifficulty
import com.ditdah.features.freemode.home.viewmodel.FreemodeHomeEffect
import com.ditdah.features.freemode.home.viewmodel.FreemodeHomeEvent
import com.ditdah.features.freemode.home.viewmodel.FreemodeHomeViewModel

@Composable
fun FreemodeHomeScreen(
    viewModel: FreemodeHomeViewModel = hiltViewModel(),
    onPlay: (FreemodeDifficulty) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effects.collect {
            when (it) {
                is FreemodeHomeEffect.Play -> { onPlay(it.difficulty) }
            }
        }
    }

    FreemodeHomeScreenContent(
        selectedDifficulty = state.selectedDifficulty,
        onSelect = {viewModel.onEvent(FreemodeHomeEvent.SelectDifficulty(it))},
        onPlay = {viewModel.onEvent(FreemodeHomeEvent.Play)}
    )
}

@Composable
private fun FreemodeHomeScreenContent(
    selectedDifficulty: FreemodeDifficulty,
    onSelect: (FreemodeDifficulty) -> Unit,
    onPlay: () -> Unit
) {

    AppScaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Свободный режим",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(16.dp))
            AppCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text(
                        text = "Изучайте азбуку Морзе в свободном режиме",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(16.dp))
                    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                        FreemodeDifficulty.entries.forEachIndexed { index, difficulty ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = FreemodeDifficulty.entries.size
                                ),
                                onClick = {onSelect(difficulty)},
                                label = { ButtonLabel(difficulty.label) },
                                selected = selectedDifficulty == difficulty
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            PrimaryButton(
                text = "Начать",
                onClick = onPlay
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun FreemodeHomeScreenPreview() {
    DitdahTheme(darkTheme = false) {
        FreemodeHomeScreenContent(
            selectedDifficulty = FreemodeDifficulty.HARD,
            onPlay = {},
            onSelect = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun FreemodeHomeScreenPreviewDark() {
    DitdahTheme(darkTheme = true) {
        FreemodeHomeScreenContent(
            selectedDifficulty = FreemodeDifficulty.HARD,
            onPlay = {},
            onSelect = {}
        )
    }
}