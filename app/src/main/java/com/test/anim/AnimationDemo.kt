package com.test.anim

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

// 动画
@Preview(showBackground = true)
@Composable
fun AnimateDpAsState_Demo() {
    var paddingDp by remember { mutableStateOf(0.dp) }
    val anim by animateDpAsState(paddingDp)  // animate*AsState 无法设置初始值
    Button(onClick = {
        paddingDp = 10.dp
    }) {
        Text("Hello", Modifier.padding(anim))
    }
}


@Preview(showBackground = true)
@Composable
fun Animatable_Demo() {
    val anim = remember { Animatable(0.dp, Dp.VectorConverter) }  // Animatable 可以设置初始值
    val coroutineScope = rememberCoroutineScope()
    Button(onClick = {
        coroutineScope.launch {
            anim.animateTo(10.dp)
//          anim.snapTo(10.dp)
        }
    }) {
        Text("Hello", Modifier.padding(anim.value))
    }
}




@Preview(showBackground = true)
@Composable
fun Keyframes_Demo() {
    Box(Modifier.size(200.dp)) {
        val smallOffset = remember { DpOffset(0.dp, 0.dp) }
        val middleOffset = remember { DpOffset(50.dp, 50.dp) }
        val bigOffset = remember { DpOffset(100.dp, 100.dp) }
        val anim = remember { Animatable(smallOffset, DpOffset.VectorConverter) }
        val coroutineScope = rememberCoroutineScope()
        Button(onClick = {
            coroutineScope.launch {
                anim.animateTo(
                    targetValue = if (anim.value == smallOffset) bigOffset else smallOffset,
                    animationSpec = keyframes { //
                        durationMillis = 2000
                        middleOffset at 1000 using FastOutSlowInEasing using ArcMode.ArcAbove //
                    }
                )
            }
        }, Modifier.offset {
            IntOffset(anim.value.x.roundToPx(), anim.value.y.roundToPx())
        }) {
            Text("Hello")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Repeatable_Demo() {
    Box(Modifier.size(200.dp)) {
        val smallOffset = remember { DpOffset(0.dp, 0.dp) }
        val bigOffset = remember { DpOffset(100.dp, 100.dp) }
        val anim = remember { Animatable(smallOffset, DpOffset.VectorConverter) }
        val coroutineScope = rememberCoroutineScope()
        Button(onClick = {
            coroutineScope.launch {
                anim.animateTo(
                    targetValue = if (anim.value == smallOffset) bigOffset else smallOffset,
                    animationSpec = repeatable(3, tween(2000), RepeatMode.Reverse) //
                )
            }
        }, Modifier.offset {
            IntOffset(anim.value.x.roundToPx(), anim.value.y.roundToPx())
        }) {
            Text("Hello")
        }
    }
}




@Preview(showBackground = true)
@Composable
fun Transition_Demo() {
    Box(Modifier.size(90.dp)) {
        var big by remember { mutableStateOf(false) }
        val transition = updateTransition(big) // Transition 可同时控制多个属性
        val size by transition.animateDp { if (it) 60.dp else 30.dp }
        Box(Modifier
            .size(size)
            .background(Color.Green)
            .clickable { big = !big }
        )
    }
}