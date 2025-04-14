package com.example.kton.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = RED_0,
    onPrimary = BLACK_2,
    secondary = GREEN_0,
    onSecondary = BLACK_2,
    surface = BLACK_1,
    onSurface = WHITE_0,
    surfaceVariant = BLACK_2,
    onSurfaceVariant = WHITE_0,
    background = BLACK_0,
    onBackground = WHITE_0
)

private val LightColorScheme = lightColorScheme(
    primary = RED_0,
    onPrimary = WHITE_0,
    secondary = GREEN_0,
    onSecondary = WHITE_0,
    surface = WHITE_1,
    onSurface = BLACK_0,
    surfaceVariant = WHITE_2,
    onSurfaceVariant = BLACK_0,
    background = WHITE_0,
    onBackground = BLACK_0
)

object Paddings {
    val small = 8.dp
    val medium = 16.dp
    val large = 24.dp
}
val LocalPaddings = staticCompositionLocalOf {
    Paddings // Valor por defecto
}

val MaterialTheme.paddings: Paddings
    @Composable
    get() = LocalPaddings.current

@Composable
fun KtonTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    CompositionLocalProvider(LocalPaddings provides Paddings) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}
