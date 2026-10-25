package com.test.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun ThemeDemo() {
    Column {
        MyTheme1 {
            Text(
                text = "Hello",
                style = TextStyle(background = LocalWarn.current)
            )
        }

        MyTheme2(MyColor) {
            Text(
                text = "Hello",
                style = TextStyle(background = MyTheme.myColors)
            )
        }
    }
}