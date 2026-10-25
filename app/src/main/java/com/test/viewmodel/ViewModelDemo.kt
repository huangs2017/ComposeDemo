package com.test.viewmodel

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

// 模拟数据模型
data class Song(val id: Int, val title: String)

@Preview(showBackground = true)
@Composable
fun PreviewMusic() {
  MusicHome()
}

@Composable
fun MusicHome(viewModel: MusicViewModel = viewModel()) {
  // 1.定义滚动行为 (向上滚动时折叠，只留小标题)
  val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

  val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
  val songs by viewModel.songs.collectAsStateWithLifecycle()  // 模拟歌单数据

  Scaffold(
    Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), // 2.绑定嵌套滚动
    // Slot A: 顶部栏
    topBar = {
      LargeTopAppBar(
        title = { Text("歌曲列表") },
        navigationIcon = {
          IconButton(onClick = { }) {
            Icon(Icons.Default.Menu, contentDescription = "菜单")
          }
        },
        actions = {
          IconButton(onClick = { }) {
            Icon(Icons.Default.Search, contentDescription = "搜索")
          }
        },
        scrollBehavior = scrollBehavior // 3.关联行为
      )
    },
    // Slot B: 底部导航栏
    bottomBar = {
      NavigationBar {
        NavigationBarItem(
          icon = { Icon(Icons.Default.Home, null) },
          label = { Text("主页") },
          selected = selectedTab == 0,
          onClick = { viewModel.selectTab(0) }
        )
        NavigationBarItem(
          icon = { Icon(Icons.Default.LibraryMusic, null) },
          label = { Text("我的") },
          selected = selectedTab == 1,
          onClick = { viewModel.selectTab(1) }
        )
      }
    },
  ) { padding ->
    // Slot C: 主内容区
    // 4. 必须是可滚动组件，折叠动画才能正常触发
    LazyColumn(contentPadding = padding) {
      items(songs) { song ->
        Text(song.title, Modifier.padding(20.dp))
      }
    }
  }
}