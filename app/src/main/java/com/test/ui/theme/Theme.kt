package com.test.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

@Composable
fun WarnTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalWarn provides Color2) {
        MaterialTheme(
            content = content
        )
    }
}

@Composable
fun MyTheme(color: Color, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalColors provides color) {
        content()
    }
}