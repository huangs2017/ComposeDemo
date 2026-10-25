package com.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset

// Modifier.layout 示例
@Preview(showBackground = true)
@Composable
fun Demo1() {
    Text("Hello",
        Modifier.layout { measurable, constraints -> // measurable
//            Modifier.layout 同时修改 尺寸+位置
//            相当于自定义view的onMeasure、layout方法
            val placeable = measurable.measure(constraints)
            layout(placeable.width, placeable.height) {
                placeable.placeRelative(x = 0, y = 0)
            }
        }
    )
}



// Layout 示例
@Preview(showBackground = true)
@Composable
fun Demo2() {
    val bitmap = ImageBitmap(width = 100, height = 100)
    // 无子组件的Layout函数
    Layout { measurables, constraints ->
        layout(bitmap.width, bitmap.height) {}
    }

    CustomLayout {
        Box(Modifier.size(20.dp).background(Color.Red))
        Box(Modifier.size(20.dp).background(Color.Red))
    }
}

@Composable
fun CustomLayout(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    // 实用的场景：有子组件的Layout函数（content是子组件）
    Layout(content, modifier) { measurables, constraints -> // measurables
        var remainedConstraints = constraints
        var totalWidth = 0
        var totalHeight = 0
        val placeables = mutableListOf<Placeable>()
        measurables.forEach {
            val placeable = it.measure(remainedConstraints).apply { placeables.add(this) }
            remainedConstraints = remainedConstraints.offset(horizontal = -placeable.width, vertical = -placeable.height)
                    totalWidth += placeable.width
                    totalHeight += placeable.height
        }
        layout(totalWidth, totalHeight) { //
            var offsetX = 0
            var offsetY = 0
            placeables.forEach {
                it.placeRelative(x = offsetX, y = offsetY)
                offsetX += it.width
                offsetY += it.height
            }
        }
    }
}