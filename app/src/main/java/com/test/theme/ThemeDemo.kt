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
            Text("Hello",
                style = TextStyle(background = local1.current)
            )
        }

        MyTheme2(color1) {
            Text("Hello",
                style = TextStyle(background = MyTheme.myColors)
            )
        }
    }
}