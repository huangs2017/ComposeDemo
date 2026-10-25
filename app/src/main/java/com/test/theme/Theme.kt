package com.test.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

@Composable
fun MyTheme1(content: @Composable () -> Unit) {
    CompositionLocalProvider(local1 provides color2) {
        MaterialTheme(content = content)
    }
}

@Composable
fun MyTheme2(color: Color, content: @Composable () -> Unit) {
    CompositionLocalProvider(local2 provides color) {
        content()
    }
}