package com.test.draw

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Preview
@Composable
private fun DrawModifierNodeDemo() {

  Box( Modifier.size(100.dp).then(BorderElement(Color.Red, 2.dp)) )

//  Box(Modifier.size(100.dp).drawWithCache {
//      onDrawBehind {
//        drawRect(
//          color = Color.Red,
//          size = size,
//          style = Stroke(width = 2.dp.toPx())
//        )
//      }
//    }
//  )

}

// 1.定义 Node
class BorderNode(var color: Color, var strokeWidth: Dp) : DrawModifierNode, Modifier.Node() {
  override fun ContentDrawScope.draw() {
    // 绘制自定义边框
    drawRect(
      color = color,
      size = size,
      style = Stroke(width = strokeWidth.toPx())
    )
  }
}

// 2.创建 Element 将 Node 包装起来
data class BorderElement(val color: Color, val strokeWidth: Dp) : ModifierNodeElement<BorderNode>() {
  override fun create(): BorderNode {
    return BorderNode(color, strokeWidth)
  }

  override fun update(node: BorderNode) {
    node.color = color
    node.strokeWidth = strokeWidth
  }
}