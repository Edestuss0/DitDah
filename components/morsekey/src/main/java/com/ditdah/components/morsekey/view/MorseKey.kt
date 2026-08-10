package com.ditdah.components.morsekey.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ditdah.components.morsekey.viewmodel.MorseKeyEvents
import com.ditdah.components.morsekey.viewmodel.MorseKeyState
import com.ditdah.components.morsekey.viewmodel.MorseKeyViewModel
import com.ditdah.core.designsystem.component.AppBadge
import com.ditdah.core.designsystem.component.AppCard
import com.ditdah.core.designsystem.theme.DitdahTheme
import com.ditdah.core.designsystem.component.PrimaryButton

@Composable
fun MorseKey(
    modifier: Modifier = Modifier,
    viewModel: MorseKeyViewModel = hiltViewModel(),
    onChange: (String) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.currentInput) {
        onChange(state.currentInput)
    }

    MorseKeyContent(
        state = state,
        onPress = { viewModel.onEvent(MorseKeyEvents.Press) },
        onRelease = { viewModel.onEvent(MorseKeyEvents.Release) },
        modifier = modifier,
        onDelete = { viewModel.onEvent(MorseKeyEvents.Delete) },
        onSpace = { viewModel.onEvent(MorseKeyEvents.Space) }
    )
}

@Composable
private fun MorseKeyContent(
    state: MorseKeyState,
    onPress: () -> Unit,
    onRelease: () -> Unit,
    modifier: Modifier,
    onSpace: () -> Unit,
    onDelete: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (state.isPressed) 0.96f else 1.0f,
        animationSpec = spring(dampingRatio = 0.55f, stiffness = Spring.StiffnessMediumLow),
        label = "key_scale"
    )

    val keyColor by animateColorAsState(
        targetValue = if (state.isPressed) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.surfaceContainerHigh
        },
        label = "key_color"
    )

    val keyContentColor by animateColorAsState(
        targetValue = if (state.isPressed) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        },
        label = "key_content_color"
    )

    val borderAlpha by animateFloatAsState(
        targetValue = if (state.isPressed) 0.8f else 0.12f,
        label = "key_border"
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AppCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = state.currentInput.ifEmpty { "Введите текст ключом..." },
                    style = MaterialTheme.typography.titleMedium,
                    color = if (state.currentInput.isEmpty()) {
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )

                AppBadge(
                    text = state.currentMorseInput.ifEmpty { "Ввод" },
                )
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .pointerInput(Unit) {
                    awaitEachGesture {
                        val down = awaitFirstDown()
                        down.consume()
                        onPress()
                        val up = waitForUpOrCancellation()
                        if (up != null) {
                            up.consume()
                            onRelease()
                        }
                    }
                },
            shape = MaterialTheme.shapes.extraLarge,
            color = keyColor,
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = borderAlpha)),
            shadowElevation = if (state.isPressed) 2.dp else 6.dp
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.TouchApp,
                        contentDescription = null,
                        tint = keyContentColor,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = if (state.isPressed) "Сигнал передаётся..." else "Удерживайте для ввода",
                        style = MaterialTheme.typography.titleMedium,
                        color = keyContentColor
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "· короткое  /  – длинное",
                        style = MaterialTheme.typography.bodySmall,
                        color = keyContentColor.copy(alpha = 0.7f)
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PrimaryButton(
                text = "Пробел",
                onClick = onSpace,
                modifier = Modifier.weight(1f)
            )

            FilledIconButton(
                onClick = onDelete,
                modifier = Modifier.size(48.dp),
                shape = MaterialTheme.shapes.medium,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Backspace,
                    contentDescription = "Удалить"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MorseKeyContentPreview() {
    DitdahTheme(darkTheme = true) {
        MorseKeyContent(
            state = MorseKeyState(
                currentMorseInput = "·–·",
                currentInput = "TRAKTARISTKA"
            ),
            onPress = {},
            onRelease = {},
            modifier = Modifier.padding(16.dp),
            onSpace = {},
            onDelete = {}
        )
    }
}