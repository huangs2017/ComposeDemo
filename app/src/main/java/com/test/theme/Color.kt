package com.test.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val color1 = Color(0xFFFF0000)
val color2 = Color(0xFF00FF00)
val local1 = staticCompositionLocalOf { color1 }
val local2 = staticCompositionLocalOf { color2 }

object MyTheme {
    val myColors
        @Composable get() = local2.current
}