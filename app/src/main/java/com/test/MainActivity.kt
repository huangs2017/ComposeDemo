package com.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.test.ui.theme.LocalWarn
import com.test.ui.theme.MyColor
import com.test.ui.theme.MyTheme
import com.test.ui.theme.WarnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Demo()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Demo() {
    Column {
        WarnTheme {
            Text(
                text = "Hello",
                style = TextStyle(background = LocalWarn.current)
            )
        }

        MyTheme(MyColor) {
            Text(
                text = "Hello",
                style = TextStyle(background = MyTheme.myColors)
            )
        }
    }
}