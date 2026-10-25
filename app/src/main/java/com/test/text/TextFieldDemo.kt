package com.test.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun TextFieldDemo() {
  val state = TextFieldState("Hello")
  Column(
    Modifier.padding(10.dp).width(100.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    BasicTextField(state)
    TextField(state)
    OutlinedTextField(state)

    Button(onClick = { state.clearText() }) {
      Text("清空")
    }
  }
}


@Preview(showBackground = true)
@Composable
fun TextFieldDemo2() {
  Box(Modifier.padding(10.dp)) {
    val state = rememberTextFieldState("Hello")

    BasicTextField(
      state,
      decorator = { innerTextField ->
        Column(Modifier.width(IntrinsicSize.Min)) {
          innerTextField()
          Box( Modifier.height(2.dp).fillMaxWidth().background(Color.Black) )
        }
      }
    )
  }
}