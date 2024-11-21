package com.skylissh.mbooking.ui.composables.blocks

import android.content.res.Configuration
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skylissh.mbooking.R
import com.skylissh.mbooking.ui.composables.core.Skeleton
import com.skylissh.mbooking.ui.theme.MBookingTheme

@Composable
fun SkeletonSliderItem(modifier: Modifier = Modifier) {
  Column(modifier = modifier.width(173.dp)) {
    Skeleton(
      modifier = Modifier
        .height(200.dp)
        .fillMaxWidth()
        .clip(RoundedCornerShape(dimensionResource(R.dimen.rounded_large)))
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
    Skeleton(
      modifier = Modifier
        .height(dimensionResource(R.dimen.padding_large))
        .width(220.dp)
        .clip(CircleShape)
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
    Skeleton(
      modifier = Modifier
        .height(dimensionResource(R.dimen.padding_medium))
        .width(140.dp)
        .clip(CircleShape)
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_xs)))
    Skeleton(
      modifier = Modifier
        .height(dimensionResource(R.dimen.padding_medium))
        .width(100.dp)
        .clip(CircleShape)
    )
  }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SkeletonSliderItemPreview() {
  MBookingTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      SkeletonSliderItem(
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
      )
    }
  }
}
