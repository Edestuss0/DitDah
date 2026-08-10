package com.ditdah.components.audioinput.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ditdah.components.audioinput.viewmodel.MorseAudioEvent
import com.ditdah.components.audioinput.viewmodel.MorseAudioViewModel
import com.ditdah.core.designsystem.component.AppTextField
import com.ditdah.core.designsystem.component.PrimaryButton

@Composable
fun MorseAudioInput(
    viewModel: MorseAudioViewModel = hiltViewModel(),
    text: String,
    onValueChange: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.input) {
        onValueChange(state.input)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PrimaryButton(
            text = if (state.isPlaying) "Воспроизведение..." else "Прослушать Морзе",
            onClick = { viewModel.onEvent(MorseAudioEvent.Play(text)) },
            enabled = !state.isPlaying,
        ) {
            Icon(
                imageVector = if (state.isPlaying) Icons.Default.GraphicEq else Icons.Default.VolumeUp,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
        AppTextField(
            value = state.input,
            onValueChange = { viewModel.onEvent(MorseAudioEvent.Input(it)) },
            label = "Вводите перевод"
        )
    }
}