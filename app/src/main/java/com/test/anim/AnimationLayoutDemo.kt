package com.test.anim

import android.util.Log
import androidx.compose.animation.animateBounds
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.DeferredTargetAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.layout.approachLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

// 布局动画
@Preview(showBackground = true)
@Composable
private fun ApproachLayout_Demo() {
    Box(Modifier.size(200.dp)) {
        var width by remember { mutableStateOf(100.dp) }

         // 布局动画1
        Box(Modifier.size(width).background(Color.Green)
            .animateContentSize(animationSpec = tween(2000)) // 只渐变尺寸
            .clickable { width = if (width == 100.dp) 150.dp else 100.dp }
        )

        // 布局动画2
//        LookaheadScope {
//            Box(Modifier.size(width).background(Color.Green)
//                .animateBounds(this@LookaheadScope) // 实时重布局，渐变尺寸+位置
//                .clickable { width = if (width == 100.dp) 150.dp else 100.dp }
//            )
//        }

        // 布局动画3 (太底层不用看)
//        val anim = remember { DeferredTargetAnimation(IntSize.VectorConverter) } // DeferredTargetAnimation 延迟目标动画
//        val coroutineScope = rememberCoroutineScope()
//
//        Box(Modifier.size(width).background(Color.Green)
//                // 自定义布局动画（animateContentSize原理）
//                .approachLayout({ true }) { measurable, constraints ->
//                    Log.d("test", "lookaheadSize = $lookaheadSize")
//                    Log.e("test", "sizeAnimation = ${anim.value}")
//                    val (width, height) = anim.updateTarget(lookaheadSize, coroutineScope) //*
//                    val placeable = measurable.measure(Constraints.fixed(width, height)) //
//                    layout(placeable.width, placeable.height) { placeable.placeRelative(0, 0) }
//                }
//                .clickable { width = if (width == 100.dp) 150.dp else 100.dp }
//        )

    }
}