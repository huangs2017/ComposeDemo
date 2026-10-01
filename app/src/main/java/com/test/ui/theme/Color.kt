package com.test.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Color1 = Color(0xFF7D5700)    // 警告色
val Color2 = Color(0xFF0061A4)    // 警告色（ 限时折扣）
val LocalWarn = staticCompositionLocalOf { Color1 }


val MyColor = Color(0xFFFF0000)
val LocalColors = staticCompositionLocalOf { MyColor }

object MyTheme {
    val myColors
        @Composable get() = LocalColors.current
}