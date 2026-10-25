package com.test.draw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
private fun DrawDemo() {
  Text("扔物线", Modifier
    .drawWithCache {
      val brush = Brush.linearGradient(listOf(Color.Red, Color.Yellow))
      onDrawBehind {
        drawRect(brush)
      }
    }
    .drawWithContent {
      drawContent()
      drawLine(
        Color.White, Offset(0f, 0f), Offset(size.width, size.height),
        strokeWidth = 4.dp.toPx()
      )
    })
}




@Preview(showBackground = true)
@Composable
fun DrawDemo2() {
  Canvas(Modifier.size(100.dp)) {
    drawLine(
      Color.Red, Offset(0f, 0f), Offset(size.width, size.height),
      strokeWidth = 4.dp.toPx()
    )
  }
}