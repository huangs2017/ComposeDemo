package com.test.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ParentDataModifierNode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun ParentDataDemo() {
  ParentDataLayout {
    Box(Modifier.clip(CircleShape).background(Color.Gray))
    Box(Modifier.clip(CircleShape).background(Color.Blue).core())
    Box(Modifier.clip(CircleShape).background(Color.Gray))
  }
}

data class DevInfo(val isCore: Boolean)

class CustomParentDataNode(var devInfo: DevInfo) : Modifier.Node(), ParentDataModifierNode {
  override fun Density.modifyParentData(parentData: Any?): DevInfo {
    if (parentData == null) return devInfo
    return DevInfo(devInfo.isCore)
  }
}

data class CustomParentDataModifier(val devInfo: DevInfo) : ModifierNodeElement<CustomParentDataNode>() {
  override fun create(): CustomParentDataNode = CustomParentDataNode(devInfo)

  override fun update(node: CustomParentDataNode) {
    node.devInfo = devInfo
  }
}

fun Modifier.core() = this.then(CustomParentDataModifier(DevInfo( true)))

@Composable
fun ParentDataLayout(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
  Layout(content, modifier) { measurables, constraints ->
    val placeables = measurables.map {
      val isCore = (it.parentData as? DevInfo)?.isCore
      val size = (if (isCore == true) 64 else 40).dp.roundToPx()
      it.measure(Constraints.fixed(size, size))
    }
    val width = placeables.sumOf { it.width }
    val height = placeables.maxOf { it.height }
    layout(width, height) {
      var offsetX = 0
      placeables.forEach { placeable ->
        val offsetY = (height - placeable.height) / 2
        placeable.placeRelative(offsetX, offsetY)
        offsetX += placeable.width
      }
    }
  }
}