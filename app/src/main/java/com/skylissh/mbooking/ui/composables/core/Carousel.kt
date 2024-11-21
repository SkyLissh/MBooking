package com.skylissh.mbooking.ui.composables.core

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue

@Composable
fun <T> Carousel(
  items: List<T>,
  modifier: Modifier = Modifier,
  initialPage: Int = 0,
  content: @Composable (PagerScope.(T, Float) -> Unit),
) {
  val pagerState = rememberPagerState(initialPage = initialPage) { items.size }

  Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
    HorizontalPager(
      state = pagerState,
      contentPadding = PaddingValues(horizontal = 50.dp)
    ) { page ->
      val offset =
        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

      content(this, items[page], offset)
    }
  }
}
