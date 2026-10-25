package com.test.touch

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// 触摸视效-波纹效果
@Preview(showBackground = true)
@Composable
fun ScaleIndicationDemo() {
    Column {
      Box(Modifier.padding(10.dp).size(100.dp)
        .clickable { }
        .background(MaterialTheme.colorScheme.primary),
      ) { Text("波纹") }

      Box(Modifier.padding(10.dp).size(100.dp)
        .clickable(
          interactionSource = remember { MutableInteractionSource() }, //
          indication = ripple(color = Color.Green) //
        ) { }
        .background(MaterialTheme.colorScheme.primary),
      ) { Text("Green 波纹") }

      Box(Modifier.padding(10.dp).size(100.dp)
        .clickable(
          interactionSource = remember { MutableInteractionSource() }, //
          indication = ScaleIndication //
        ) { }
        .background(MaterialTheme.colorScheme.primary),
      ) { Text("点我放大") }
    }
}


data object ScaleIndication : IndicationNodeFactory {
  override fun create(interactionSource: InteractionSource): DelegatableNode {
    return ScaleIndicationNode(interactionSource)
  }
}

private class ScaleIndicationNode(private val interactionSource: InteractionSource) :
  DrawModifierNode, Modifier.Node() {

  private val scaleAnim = Animatable(1f)

  override fun onAttach() {
    coroutineScope.launch {
      interactionSource.interactions.collectLatest { interaction ->
        when (interaction) {
          is PressInteraction.Press -> {
            scaleAnim.animateTo(targetValue = 1.5f)
          }

          is PressInteraction.Release, is PressInteraction.Cancel -> {
            scaleAnim.animateTo(targetValue = 1f)
          }
        }
      }
    }
  }

  override fun ContentDrawScope.draw() {
    scale(scaleAnim.value) { this@draw.drawContent() }
  }
}