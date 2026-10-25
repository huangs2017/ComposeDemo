package com.test.anim

import androidx.compose.animation.animateBounds
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.R

// 跨层级的动画
// （介于普通布局动画和共享元素动画之间的动画）
@Preview
@Composable
private fun Demo() {
    var vertical by remember { mutableStateOf(false) }
    LookaheadScope {
        val content = remember {
            movableContentOf {
                Image(
                    painterResource(id = R.drawable.avatar),
                    contentDescription = null,
                    Modifier.size(100.dp).animateBounds(this@LookaheadScope)
                )
                Text("Hello", Modifier.animateBounds(this@LookaheadScope))
            }
        }

        Box(Modifier.size(200.dp).clickable { vertical = !vertical }) {
            if (vertical) {
                Column(Modifier.padding(8.dp).background(Color.White)) {
                    content()
                }
            } else {
                Row(Modifier.padding(8.dp).background(Color.White)) {
                    content()
                }
            }
        }
    }
}