package com.ditdah.core.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary                  = PrimaryLight,
    onPrimary                = OnPrimaryLight,
    primaryContainer         = PrimaryContainerLight,
    onPrimaryContainer       = OnPrimaryContainerLight,
    inversePrimary           = Orange80,

    secondary                = SecondaryLight,
    onSecondary               = Color.White,
    secondaryContainer       = Brown90,
    onSecondaryContainer     = Brown10,

    tertiary                  = Neutral40,
    onTertiary                = Color.White,
    tertiaryContainer         = Neutral80,
    onTertiaryContainer       = Dark40,

    error                     = ErrorRed,
    onError                   = Color.White,
    errorContainer            = ErrorContainerLight,
    onErrorContainer          = OnErrorContainerLight,

    background                = BackgroundLight,
    onBackground               = OnLight,
    surface                    = SurfaceLight,
    onSurface                  = OnLight,
    surfaceVariant             = SurfaceVariantLight,
    onSurfaceVariant           = OnLightMuted,
    surfaceTint                = PrimaryLight,

    outline                    = OutlineLight,
    outlineVariant              = BorderLight,

    inverseSurface              = InverseSurfaceLight,
    inverseOnSurface            = InverseOnSurfaceLight,
    scrim                       = ScrimColor,

    surfaceDim                  = SurfaceDimLight,
    surfaceBright               = SurfaceBrightLight,
    surfaceContainerLowest      = SurfaceContainerLowestLight,
    surfaceContainerLow         = SurfaceContainerLowLight,
    surfaceContainer            = SurfaceContainerLight,
    surfaceContainerHigh        = SurfaceContainerHighLight,
    surfaceContainerHighest     = SurfaceContainerHighestLight,
)

private val DarkColorScheme = darkColorScheme(
    primary                  = PrimaryDark,
    onPrimary                = OnPrimaryDark,
    primaryContainer         = PrimaryContainerDark,
    onPrimaryContainer       = OnPrimaryContainerDark,
    inversePrimary            = Orange40,

    secondary                 = SecondaryDark,
    onSecondary                = Brown10,
    secondaryContainer        = Brown30,
    onSecondaryContainer      = Brown90,

    tertiary                   = Neutral80,
    onTertiary                 = Dark40,
    tertiaryContainer          = Dark40,
    onTertiaryContainer        = Neutral80,

    error                      = ErrorRedDark,
    onError                    = OnErrorDark,
    errorContainer             = ErrorContainerDark,
    onErrorContainer           = OnErrorContainerDark,

    background                 = BackgroundDark,
    onBackground                = OnDark,
    surface                     = SurfaceDark,
    onSurface                   = OnDark,
    surfaceVariant              = SurfaceVariantDark,
    onSurfaceVariant            = OnDarkMuted,
    surfaceTint                 = PrimaryDark,

    outline                     = OutlineDark,
    outlineVariant               = BorderDark,

    inverseSurface               = InverseSurfaceDark,
    inverseOnSurface             = InverseOnSurfaceDark,
    scrim                        = ScrimColor,

    surfaceDim                   = SurfaceDimDark,
    surfaceBright                = SurfaceBrightDark,
    surfaceContainerLowest       = SurfaceContainerLowestDark,
    surfaceContainerLow          = SurfaceContainerLowDark,
    surfaceContainer             = SurfaceContainerDark,
    surfaceContainerHigh         = SurfaceContainerHighDark,
    surfaceContainerHighest      = SurfaceContainerHighestDark,
)

@Composable
fun DitdahTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}