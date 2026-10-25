package com.test.touch

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// 演示场景 1: 基础点击手势
@Preview(showBackground = true)
@Composable
fun ClickDemo() {
    var pressed by remember { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(100.dp)
            .clip(shape = RoundedCornerShape(size = 24.dp))
            .background(color = if (pressed) Color.Gray else Color.Blue)
            .pointerInput(key1 = Unit) {
                awaitEachGesture {
                    awaitFirstDown().consume() // 1.等待按下
                    pressed = true
                    val up = waitForUpOrCancellation() // 2.等待抬起
                    pressed = false
                    up?.consume()
                }
            }
    ) {
        Text("基础点击")
    }
}


//@Preview(showBackground = true)
@Composable
private fun PointerInputDemo() {
    var text by remember { mutableStateOf("Hello") }
    Button(onClick = {}, Modifier.pointerInput(key1 = Unit) {
        detectTapGestures(onPress = {
            // set background
            tryAwaitRelease()
            // set background back
        })
        detectDragGestures { change, dragAmount -> }
    }) {
        Text(text)
    }
}