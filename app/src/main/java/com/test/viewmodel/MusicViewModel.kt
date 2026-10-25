package com.test.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MusicViewModel : ViewModel() {

  private val _songs = MutableStateFlow(List(20) { index ->
    Song(id = index, title = "歌曲${index + 1}")
  })
  private val _selectedTab = MutableStateFlow(0)

  val songs: StateFlow<List<Song>> = _songs.asStateFlow()
  val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

  fun selectTab(index: Int) {
    _selectedTab.value = index
  }

}