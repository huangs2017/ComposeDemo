package com.test.anim

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// 自带转场功能的组件

// 1.组件显隐动画
@Preview(showBackground = true)
@Composable
private fun Demo() {
    Box(Modifier.size(100.dp)) {
        var shown by remember { mutableStateOf(value = true) }
        Column {
            AnimatedVisibility(shown) { // 组件的显隐动画：淡入淡出、滑入滑出、裁切、放缩、遮罩
                Box(Modifier.size(48.dp).background(Color.Green))
            }
            Button(onClick = { shown = !shown }) { Text("切换") }
        }
    }
}


// 2.组件切换动画
@Preview(showBackground = true)
@Composable
private fun Demo2() {
    Box(Modifier.size(100.dp)) {
        var shown by remember { mutableStateOf(true) }
        Column {
            Crossfade(shown) { // 用淡入淡出，让旧组件消失、新组件出现
                if (it) {
                    Box(Modifier.size(48.dp).background(Color.Green))
                } else {
                    Box(Modifier.size(24.dp).background(Color.Red))
                }
            }
            Button(onClick = { shown = !shown }) { Text("切换") }
        }
    }
}


// 3.组件切换动画
@Preview(showBackground = true)
@Composable
private fun Demo3() {
    Box(Modifier.size(100.dp)) {
        var shown by remember { mutableStateOf(true) }
        Column {
            AnimatedContent(shown) { // 比Crossfade更强大，可以详细配置动画效果
                if (it) {
                    Box(Modifier.size(48.dp).background(Color.Green))
                } else {
                    Box(Modifier.size(24.dp).background(Color.Red))
                }
            }
            Button(onClick = { shown = !shown }) { Text("切换") }
        }
    }
}