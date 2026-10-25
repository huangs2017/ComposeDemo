package com.test.anim

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.R

// 跨界面的共享元素动画
@Preview(showBackground = true)
@Composable
private fun SharedTransitionDemo() {
    var isExpanded by remember { mutableStateOf(false) }
    val clicked = { isExpanded = !isExpanded }

    SharedTransitionLayout {
        AnimatedContent(targetState = isExpanded) { targetExpanded ->
            if (targetExpanded) {
                PageScreen(this@SharedTransitionLayout, this, clicked)
            } else {
                CardScreen(this@SharedTransitionLayout, this, clicked)
            }
        }
    }
}

@Composable
private fun CardScreen(
    transitionScope: SharedTransitionScope,
    visibilityScope: AnimatedVisibilityScope,
    clicked: () -> Unit
) {
    with(transitionScope) {
        Row(Modifier.size(200.dp).padding(8.dp)
              .sharedBounds(rememberSharedContentState("Container"), visibilityScope)
              .clickable { clicked() }
        ) {
            Image(painterResource(id = R.drawable.avatar), null, Modifier.size(100.dp)
                  .sharedElement(rememberSharedContentState("Cover"), visibilityScope)
            )
            Text("Hello")
        }
    }
}

@Composable
private fun PageScreen(
    transitionScope: SharedTransitionScope,
    visibilityScope: AnimatedVisibilityScope,
    clicked: () -> Unit
) {
    with(transitionScope) {
        Column(Modifier.size(200.dp).padding(8.dp)
              .sharedBounds(rememberSharedContentState("Container"), visibilityScope)
              .clickable { clicked() }
        ) {
            Image(painterResource(id = R.drawable.avatar), null, Modifier.size(150.dp)
                  .sharedElement(rememberSharedContentState("Cover"), visibilityScope)
            )
            Text("Hello")
            Text("广告位")
        }
    }
}