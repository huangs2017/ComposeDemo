package com.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TripleAssistChips()
        }
    }
}


// 用三个AssistChip显示Triple里的值
@Preview(showBackground = true)
@Composable
fun TripleAssistChips(triple: Triple<String, String, String> = Triple("标题1", "标题2", "标题3")) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        AssistChip(
            onClick = { },
            label = { Text(triple.first) }
        )
        AssistChip(
            onClick = { },
            label = { Text(triple.second) }
        )
        AssistChip(
            onClick = { },
            label = { Text(triple.third) }
        )
    }
}