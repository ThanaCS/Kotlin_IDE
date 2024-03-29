package com.thana.kotlin_ide.android.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


val LightTheme = lightColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryVariant,
    secondary = SecondaryColor
)
val DarkTheme = darkColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryVariant,
    secondary = SecondaryColor
)

@Composable
fun IDETheme(
    isDark: Boolean = false,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = if (isDark) DarkTheme else LightTheme,
    ) {
        Surface(color = DarkBackground) {
            content()
        }
    }
}