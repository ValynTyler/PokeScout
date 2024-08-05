package com.example.pokemon.presentation.theme

import android.app.Activity
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val TrainerColorScheme = darkColorScheme(
    primary = PokeBallRed,
    secondary = GreatBallBlue,
    background = PokeBallGrey,
    surface = PokeBallDarkGrey,
)

@Composable
fun PokeScoutTheme(
    colorScheme: ColorScheme = TrainerColorScheme,
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val statusBarColor = colorScheme.primary.copy(alpha = 1.0f)
            window.statusBarColor = statusBarColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}