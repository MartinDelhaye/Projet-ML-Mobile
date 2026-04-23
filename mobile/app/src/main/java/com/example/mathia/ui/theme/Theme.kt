package com.example.mathia.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Principal,
    secondary = Secondary,
    background = White,
    onPrimary = White,
    onBackground = Black,
    onSurface = Black
)

@Composable
fun MathIATheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}