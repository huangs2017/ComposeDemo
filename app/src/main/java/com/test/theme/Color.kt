package com.test.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Color1 = Color(0xFFFF0000)
val Color2 = Color(0xFF00FF00)
val LocalWarn = staticCompositionLocalOf { Color1 }


val MyColor = Color(0xFF0000FF)
val LocalColors = staticCompositionLocalOf { MyColor }

object MyTheme {
    val myColors
        @Composable get() = LocalColors.current
}