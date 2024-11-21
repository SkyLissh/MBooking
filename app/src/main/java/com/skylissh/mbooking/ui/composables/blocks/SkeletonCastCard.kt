package com.skylissh.mbooking.ui.composables.blocks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.skylissh.mbooking.R
import com.skylissh.mbooking.ui.composables.core.Skeleton

@Composable
fun SkeletonCastCard(modifier: Modifier = Modifier) {
  Card(
    shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_small)),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceVariant
    ),
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
      modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
    ) {
      Skeleton(
        modifier = Modifier
          .size(dimensionResource(R.dimen.avatar_size))
          .clip(CircleShape)
      )
      Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))) {
        Skeleton(
          modifier = Modifier
            .height(dimensionResource(R.dimen.padding_large))
            .width(100.dp)
        )
        Skeleton(
          modifier = Modifier
            .height(dimensionResource(R.dimen.padding_medium))
            .width(50.dp)
        )
      }
    }
  }
}
