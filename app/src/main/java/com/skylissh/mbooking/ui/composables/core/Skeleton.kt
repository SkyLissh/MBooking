package com.skylissh.mbooking.ui.composables.core

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skylissh.mbooking.ui.theme.MBookingTheme

@Composable
fun Skeleton(modifier: Modifier = Modifier) {
  val transition = rememberInfiniteTransition(label = "shimmer")
  val opacityAnimation by transition.animateFloat(
    initialValue = 0.8f,
    targetValue = 0.5f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 800, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ), label = "shimmer animation"
  )

  Box(
    modifier = modifier
      .alpha(opacityAnimation)
      .background(MaterialTheme.colorScheme.surface)
  )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SkeletonPreview() {
  MBookingTheme {
    Surface(color = MaterialTheme.colorScheme.background) {

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
      ) {
        Skeleton(modifier = Modifier.size(80.dp))
        Column(
          verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
          Skeleton(
            modifier = Modifier
              .height(24.dp)
              .fillMaxWidth()
          )
          Skeleton(modifier = Modifier.size(120.dp, 24.dp))
        }
      }
    }
  }
}
