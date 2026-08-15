package com.ditdah.core.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import com.ditdah.core.designsystem.theme.InfoBlue
import com.ditdah.core.designsystem.theme.SuccessGreen
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class SnackbarMessageType {
    SUCCESS,
    ERROR,
    INFO
}

data class AppSnackbarVisuals(
    override val message: String,
    val type: SnackbarMessageType = SnackbarMessageType.INFO,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
) : SnackbarVisuals



@Composable
fun AppSnackbarHost(
    host: SnackbarHostState, ) {
    SnackbarHost(hostState = host) {data ->
        val appVisuals = data.visuals as? AppSnackbarVisuals

        val bgColor = when (appVisuals?.type) {
            SnackbarMessageType.INFO -> InfoBlue
            SnackbarMessageType.ERROR -> MaterialTheme.colorScheme.error
            SnackbarMessageType.SUCCESS -> SuccessGreen
            else -> InfoBlue
        }

        Snackbar(
            snackbarData = data,
            containerColor = bgColor,
            contentColor = Color.White,
            actionColor = Color.White.copy(alpha = 0.85f),
            shape = RoundedCornerShape(16.dp)
        )
    }
}