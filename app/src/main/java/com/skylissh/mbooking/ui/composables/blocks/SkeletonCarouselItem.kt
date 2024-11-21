package com.skylissh.mbooking.ui.composables.blocks

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.skylissh.mbooking.R
import com.skylissh.mbooking.ui.composables.core.Skeleton
import com.skylissh.mbooking.ui.theme.MBookingTheme

@Composable
fun SkeletonCarouselItem(offset: Float, modifier: Modifier = Modifier) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
    modifier = modifier
      .graphicsLayer {
        lerp(start = 0.85f, stop = 1f, fraction = 1f - offset.coerceIn(0f, 1f)).also {
          scaleX = it
          scaleY = it
        }
        alpha = lerp(start = 0.5f, stop = 1f, fraction = 1f - offset.coerceIn(0f, 1f))
      }
  ) {
    Skeleton(
      modifier = Modifier
        .height(400.dp)
        .fillMaxWidth()
        .clip(RoundedCornerShape(dimensionResource(R.dimen.rounded_2xl)))
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
    Skeleton(
      modifier = Modifier
        .height(dimensionResource(R.dimen.padding_xl))
        .width(150.dp)
        .clip(CircleShape)
    )
    Skeleton(
      modifier = Modifier
        .height(dimensionResource(R.dimen.padding_medium))
        .width(220.dp)
        .clip(CircleShape)
    )
    Skeleton(
      modifier = Modifier
        .height(dimensionResource(R.dimen.padding_small))
        .width(100.dp)
        .clip(CircleShape)
    )
  }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SkeletonCarouselItemPreview() {
  MBookingTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      SkeletonCarouselItem(
        0f,
        modifier = Modifier.padding(
          horizontal = dimensionResource(R.dimen.padding_xl),
          vertical = dimensionResource(R.dimen.padding_medium)
        )
      )
    }
  }
}
