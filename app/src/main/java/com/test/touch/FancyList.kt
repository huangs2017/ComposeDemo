package com.test.touch

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.test.R
import kotlin.math.abs
import kotlin.math.sign

data class Post(val content: String, @DrawableRes val image: Int)

val samplePosts = listOf(
  Post("图片1", R.drawable.paris),
  Post("图片2", R.drawable.paris),
  Post("图片3", R.drawable.paris),
  Post("图片4", R.drawable.paris)
)

class RubberBandModifierNode : Modifier.Node(), LayoutModifierNode {
  var overscrollY by mutableFloatStateOf(0f)
  override fun MeasureScope.measure(measurable: Measurable, constraints: Constraints): MeasureResult {
    val placeable = measurable.measure(constraints)
    return layout(placeable.width, placeable.height) {
      placeable.placeWithLayer(0, 0) {
        translationY = overscrollY
      }
    }
  }
}

class RubberBandOverscrollEffect : OverscrollEffect {
  override val node = RubberBandModifierNode()
  override val isInProgress = node.overscrollY != 0f

  override fun applyToScroll(
    delta: Offset,
    source: NestedScrollSource,
    performScroll: (Offset) -> Offset
  ): Offset {
    val currentY = node.overscrollY
    if (currentY != 0f && sign(currentY) != sign(delta.y)) {
      node.overscrollY += delta.y / 2f
    } else {
      val unconsumed = delta - performScroll(delta)
      node.overscrollY += unconsumed.y / 2f
    }
    return delta
  }

  override suspend fun applyToFling(
    velocity: Velocity,
    performFling: suspend (Velocity) -> Velocity
  ) {
    val unconsumed = velocity - performFling(velocity)
    Animatable(node.overscrollY)
      .animateTo(0f, initialVelocity = unconsumed.y,
        animationSpec = spring(
          dampingRatio = Spring.DampingRatioLowBouncy,
          stiffness = Spring.StiffnessVeryLow
        )) { node.overscrollY = value }
  }
}

@Composable
fun FancyList(modifier: Modifier = Modifier) {
  val decaySpec = rememberSplineBasedDecay<Float>()
  val flingBehavior = remember {
    object : FlingBehavior {
      override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
        var lastValue = 0f
        var lastVelocity = initialVelocity
        AnimationState(0f, initialVelocity).animateDecay(decaySpec) {
          val delta = value - lastValue
          val consumed = scrollBy(delta)
          lastValue = value
          lastVelocity = velocity

          if (abs(delta - consumed) > 0.5f) {
            cancelAnimation()
          }
        }
        return lastVelocity
      }
    }
  }
  val rubberEffect = remember { RubberBandOverscrollEffect() }

  val displayPosts = remember { samplePosts + samplePosts }
  LazyColumn(
    modifier = modifier.fillMaxSize(),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    flingBehavior = flingBehavior,
    overscrollEffect = rubberEffect, // 过度滑动效果
  ) {
    item {
      Text(
        text = "Discover",
        style = MaterialTheme.typography.headlineLarge
      )
    }
    items(items = displayPosts) { post ->
      PostItem(post)
    }
  }
}

@Composable
fun PostItem(post: Post) {
  ElevatedCard {
    Column {
      Text(
        text = post.content,
        modifier = Modifier.padding(12.dp)
      )
      AsyncImage(
        model = post.image,
        contentDescription = "",
        modifier = Modifier.height(200.dp).padding(12.dp),
        contentScale = ContentScale.Crop
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun FancyListPreview() {
  FancyList()
}