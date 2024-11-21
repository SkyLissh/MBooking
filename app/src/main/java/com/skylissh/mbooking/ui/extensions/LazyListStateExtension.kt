package com.skylissh.mbooking.ui.extensions

import androidx.compose.foundation.lazy.LazyListState

internal fun LazyListState.reachedBottom(buffer: Int = 1): Boolean {
  val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index

  return lastVisibleIndex?.let { lastVisibleIndex + buffer >= layoutInfo.totalItemsCount } == true
}
