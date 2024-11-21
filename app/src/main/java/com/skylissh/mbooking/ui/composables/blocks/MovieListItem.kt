package com.skylissh.mbooking.ui.composables.blocks

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.composables.core.Icon
import com.composables.icons.lucide.Calendar
import com.composables.icons.lucide.ImageOff
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Star
import com.skylissh.mbooking.BuildConfig
import com.skylissh.mbooking.R
import com.skylissh.mbooking.data.models.Movie
import com.skylissh.mbooking.ui.mockedMovie
import com.skylissh.mbooking.ui.theme.MBookingTheme
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter

@Composable
fun MovieListItem(movie: Movie, modifier: Modifier = Modifier, onClick: (Int) -> Unit = {}) {
  val locale = LocalConfiguration.current.locales.get(0)
  val formattedReleaseDate = remember {
    if (movie.releaseDate == null) return@remember "---"
    DateTimeFormatter.ofPattern("dd.MM.yyyy").format(movie.releaseDate.toJavaLocalDate())
  }

  Card(
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface,
      contentColor = MaterialTheme.colorScheme.onSurface
    ),
    onClick = { onClick(movie.id) },
    modifier = modifier.height(140.dp)
  ) {
    Row {
      if (movie.posterPath != null) {
        AsyncImage(
          model = "${BuildConfig.TMDB_API_IMAGE_URL}${movie.posterPath}",
          contentDescription = stringResource(R.string.movie_poster),
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(656f / 985f)
        )
      } else {
        Box(
          modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(656f / 985f)
        ) {
          Icon(
            imageVector = Lucide.ImageOff,
            contentDescription = stringResource(R.string.movie_poster_not_found),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
              .size(48.dp)
              .align(Alignment.Center)
          )
        }
      }
      Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_large))) {
        Text(
          text = movie.title,
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
          Icon(
            imageVector = Lucide.Calendar,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(16.dp)
          )
          Text(
            text = formattedReleaseDate,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
          Icon(
            imageVector = Lucide.Star,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(16.dp)
          )
          Text(
            text = "${"%.2f".format(locale, movie.voteAverage)} (${movie.voteCount})",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
      }
    }
  }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieListItemPreview() {
  MBookingTheme {
    Surface(
      color = MaterialTheme.colorScheme.background,
    ) {
      MovieListItem(
        movie = mockedMovie,
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
      )
    }
  }
}
