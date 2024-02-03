package com.example.clientnewsvk.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Black900,
    onPrimary = Color.White,
    secondary = Black900,
    onSecondary = Black500,
    background = Black900
)

private val LightColorScheme = lightColorScheme(
    primary = Color.White,
    onPrimary = Black900,
    secondary = Color.White,
    onSecondary = Black500,
    background = Color.White
)

@Composable
fun ClientNewsVKTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme){
        DarkColorScheme
    }else{
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}