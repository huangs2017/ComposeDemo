package com.test.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Modifier.layout 单个组件测量和摆放
// Layout 完整的布局容器, 可以同时测量、摆放多个子元素（Row、Column、Box 底层全是用它实现的）

// 1.Modifier.layout 示例
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



// 2.Layout 示例
@Preview(showBackground = true)
@Composable
fun Demo2() {
//    val bitmap = ImageBitmap(width = 100, height = 100)
//    // 无子组件的Layout函数
//    Layout { measurables, constraints ->
//        layout(bitmap.width, bitmap.height) {}
//    }

    // 有子组件的Layout函数
    CustomColumn {
        Box(Modifier.size(20.dp).background(Color.Red))
        Box(Modifier.size(30.dp).background(Color.Green))
    }
}

@Composable
fun CustomColumn(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    // 实用的场景：有子组件的Layout函数（content 子组件集合）
    Layout(content, modifier) { measurables, constraints -> // measurables
        // 1.测量所有子组件
        val placeList = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        // 2.计算自定义容器的总宽高
        var totalHeight = 0
        var maxWidth = 0
        placeList.forEach { placeable ->
            maxWidth = maxOf(maxWidth, placeable.width)
            totalHeight += placeable.height
        }

        // 3.放置子组件
        layout(maxWidth, totalHeight) {
            var yPosition = 0
            placeList.forEach { placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}