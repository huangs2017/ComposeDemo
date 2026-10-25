package com.test.draw

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
private fun TicketDemo() {
  val notchFraction = 0.7f // 槽口的位置
  val shape = remember(notchFraction) {
    TicketShape(cornerRadius = 12.dp, notchRadius = 8.dp, notchFraction = notchFraction)
  }

  Row(
    Modifier.padding(10.dp).width(200.dp).height(100.dp).clip(shape) // 外层通过 clip 切出票的形状
  ) {
    // 左边部分：电影介绍
    Column(
      Modifier
        .weight(notchFraction)
        .fillMaxHeight()
        .background(Color(0xFF2196F3))
    ) { }
    // 右边部分：座位信息
    Column(
      Modifier
        .weight(1f - notchFraction)
        .fillMaxHeight()
        .background(Color(0xFFE91E63)),
    ) { }
  }
}

class TicketShape(private val cornerRadius: Dp, private val notchRadius: Dp, private val notchFraction: Float) : Shape {

  override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
    val width = size.width
    val height = size.height

    val r = with(density) { cornerRadius.toPx() }
    val notchR = with(density) { notchRadius.toPx() }
    val notchCenterX = (width * notchFraction)

    // 1. 基础圆角矩形
    val baseRect = Path().apply {
      addRoundRect(RoundRect(0f, 0f, width, height, CornerRadius(r)))
    }

    // 2. 顶部的圆形缺口
    val topNotch = Path().apply {
      addOval(Rect(notchCenterX - notchR, -notchR, notchCenterX + notchR, notchR))
    }

    // 3. 底部的圆形缺口
    val bottomNotch = Path().apply {
      addOval(Rect(notchCenterX - notchR, height - notchR, notchCenterX + notchR, height + notchR))
    }

    // 通过减法运算，得到最终结果
    val finalPath = baseRect - topNotch - bottomNotch

    return Outline.Generic(finalPath)
  }
}