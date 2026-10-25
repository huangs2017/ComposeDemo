package com.test.touch

import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.test.R

@Preview(showBackground = true)
@Composable
fun TransformableDemo() {
  var scale by remember { mutableFloatStateOf(1f) }
  var translation by remember { mutableStateOf(Offset.Zero) }
  var rotation by remember { mutableFloatStateOf(0f) }

  val state = rememberTransformableState { _, zoomChange, panChange, rotationChange ->
    scale *= zoomChange
    translation += panChange
    rotation += rotationChange
  }

  AsyncImage(
    model = R.drawable.avatar,
    contentDescription = null,
    modifier = Modifier
      .size(300.dp)
      .graphicsLayer(
        scaleX = scale,
        scaleY = scale,
        translationX = translation.x,
        translationY = translation.y,
        rotationZ = rotation
      )
      .transformable(state) // 缩放、平移与旋转手势
  )
}