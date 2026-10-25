package com.test.text

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.test.R

@Preview(showBackground = true)
@Composable
private fun TextDemo() {
  val text = remember {
    buildAnnotatedString {
      append("我是")
      withStyle(SpanStyle(fontSize = 24.sp)) {
        append("扔物线")
        withStyle(SpanStyle(fontSize = 12.sp, baselineShift = BaselineShift.Superscript)) {
          append("朱凯")
        }
      }

      append("\n")
      val linkUrl = LinkAnnotation.Url("baidu.com") { }
      withLink(linkUrl) { append("baidu.com") }

      append("\n")
      val linkClick = LinkAnnotation.Clickable("聊天") { }
        withLink(linkClick) { append("聊天") }

      withBulletList {
        withBulletListItem {
          append("标题1")
        }
      }
    }
  }
  Text(text)
}



@Preview(showBackground = true)
@Composable
private fun BasicTextDemo() {
  Box {
    val avatarId = "avatar"
    val annotatedText = buildAnnotatedString {
      append("插入图片")
      appendInlineContent(avatarId)
    }

    BasicText(
      annotatedText, inlineContent = mapOf(
        avatarId to InlineTextContent(
          Placeholder(1.em, 1.em, PlaceholderVerticalAlign.TextCenter)
        ) {
          Image(painterResource(R.drawable.avatar), "头像")
        }
      ))


  }
}