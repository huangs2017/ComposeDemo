package com.test.navigation3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable

data class Song(val id: Int, val title: String)

val songList = List(10) { index ->
  Song(index, "歌曲${index + 1}")
}

// ==========================================
// 1. 路由定义层
// ==========================================
  @Serializable // 路由对象加 @Serializable
  data object HomeRoute : NavKey
  @Serializable
  data class SongDetailRoute(val songId: Int) : NavKey

// ==========================================
// 2. 导航中枢
// ==========================================
@Preview(showBackground = true)
@Composable
fun Nav3Demo() {
  val backStack = rememberNavBackStack(HomeRoute)

  NavDisplay(
    backStack = backStack,

    entryProvider = entryProvider {
      entry<HomeRoute> {
        HomeScreen(
          onSongClick = { songId -> backStack.add(SongDetailRoute(songId)) }
        )
      }
      entry<SongDetailRoute> {
        val song = songList.find { song -> song.id == it.songId }
        SongDetailScreen(
          song = song!!,
          onBack = { backStack.removeLastOrNull() }
        )
      }
    }
  )
}

// ==========================================
// 3. UI 层
// ==========================================
@Composable
fun HomeScreen(onSongClick: (Int) -> Unit) {
  val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
  var selectedTab by remember { mutableIntStateOf(0) }

  Scaffold(
    Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
          IconButton(onClick = {  }) {
            Icon(Icons.Default.Search, contentDescription = "搜索")
          }
        },
        scrollBehavior = scrollBehavior
      )
    },
    // Slot B: 底部导航栏
    bottomBar = {
      NavigationBar {
        NavigationBarItem(
          icon = { Icon(Icons.Default.Home, null) },
          label = { Text("主页") },
          selected = selectedTab == 0,
          onClick = { selectedTab = 0 }
        )
        NavigationBarItem(
          icon = { Icon(Icons.Default.LibraryMusic, null) },
          label = { Text("我的") },
          selected = selectedTab == 1,
          onClick = { selectedTab = 1 }
        )
      }
    },
  ) { padding ->
    // Slot C: 主内容区
    LazyColumn(contentPadding = padding) {
      items(songList) { song ->
        Text(song.title, Modifier.padding(30.dp).clickable { onSongClick(song.id) })
      }
    }
  }
}


@Composable
fun SongDetailScreen(song: Song, onBack: () -> Unit) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(song.title) },
        navigationIcon = {
          IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
          }
        },
      )
    }
  ) { padding ->
      Text(song.title, Modifier.padding(padding))
  }
}