package com.skylissh.mbooking.ui.composables.blocks

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.User
import com.skylissh.mbooking.BuildConfig
import com.skylissh.mbooking.R
import com.skylissh.mbooking.ui.forwardingPainter
import com.skylissh.mbooking.ui.theme.MBookingTheme

@Composable
fun CastCard(avatarUrl: String?, name: String, role: String, modifier: Modifier = Modifier) {
  Card(
    shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_large)),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    modifier = modifier
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
      modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
    ) {
      AsyncImage(
        model = "${BuildConfig.TMDB_API_IMAGE_URL}${avatarUrl}",
        contentDescription = null,
        contentScale = ContentScale.Crop,
        error = forwardingPainter(
          painter = rememberVectorPainter(Lucide.User),
          colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
        ),
        modifier = Modifier
          .size(dimensionResource(R.dimen.avatar_size))
          .clip(CircleShape)
          .background(MaterialTheme.colorScheme.background.copy(alpha = 0.4f))
      )
      Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))) {
        Text(text = name, style = MaterialTheme.typography.bodyLarge)
        Text(
          text = role,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CastCardPreview() {
  MBookingTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      CastCard(
        name = "John Doe",
        role = "Actor",
        avatarUrl = "https://via.placeholder.com/150",
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
      )
    }
  }
}
