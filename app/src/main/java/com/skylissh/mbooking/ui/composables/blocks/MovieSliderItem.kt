package com.skylissh.mbooking.ui.composables.blocks

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.composables.core.Icon
import com.composables.icons.lucide.Calendar
import com.composables.icons.lucide.ImageOff
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Video
import com.skylissh.mbooking.BuildConfig
import com.skylissh.mbooking.R
import com.skylissh.mbooking.data.models.Genres
import com.skylissh.mbooking.data.models.Movie
import com.skylissh.mbooking.data.models.genres
import com.skylissh.mbooking.ui.mockedMovie
import com.skylissh.mbooking.ui.mockedMovieGenres
import com.skylissh.mbooking.ui.theme.MBookingTheme
import com.skylissh.mbooking.ui.viewmodels.AsyncState
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter

@Composable
fun MovieSliderItem(
  movie: Movie,
  genres: AsyncState<Genres>,
  onClick: (Int) -> Unit,
  modifier: Modifier = Modifier,
) {
  val releasedDate = remember {
    if (movie.releaseDate == null) return@remember "---"
    DateTimeFormatter.ofPattern("yyyy.MM.dd").format(movie.releaseDate.toJavaLocalDate())
  }

  Column(
    modifier = modifier.width(173.dp)
  ) {
    Card(
      shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_large)),
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface
      ),
      onClick = { onClick(movie.id) },
      modifier = Modifier.height(200.dp),
    ) {
      if (movie.posterPath != null) {
        AsyncImage(
          model = ImageRequest.Builder(LocalContext.current)
            .data("${BuildConfig.TMDB_API_IMAGE_URL}${movie.posterPath}")
            .crossfade(true)
            .build(),
          contentDescription = stringResource(R.string.movie_poster),
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )
      } else {
        Icon(
          imageVector = Lucide.ImageOff,
          contentDescription = stringResource(R.string.movie_poster_not_found),
          tint = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.size(84.dp)
        )
      }
    }
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
    Text(
      text = movie.title,
      style = MaterialTheme.typography.bodyLarge,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.primary,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
      Icon(
        imageVector = Lucide.Video,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.size(dimensionResource(R.dimen.icon_small))
      )
      Text(
        text = when (genres) {
          is AsyncState.Success -> movie.genres(genres.data.genres).joinToString(", ")
          is AsyncState.Loading -> ""
          is AsyncState.Error -> stringResource(R.string.no_genres_found)
        },
        style = MaterialTheme.typography.labelMedium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
    }
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_xs)))
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
      Icon(
        imageVector = Lucide.Calendar,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.size(dimensionResource(R.dimen.icon_small))
      )
      Text(text = releasedDate, style = MaterialTheme.typography.labelMedium)
    }
  }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieSliderItemSuccessPreview() {
  MBookingTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      MovieSliderItem(
        movie = mockedMovie,
        genres = AsyncState.Success(mockedMovieGenres),
        onClick = {},
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
      )
    }
  }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieSliderItemLoadingPreview() {
  MBookingTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      MovieSliderItem(
        movie = mockedMovie,
        genres = AsyncState.Loading,
        onClick = {},
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
      )
    }
  }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieSliderItemErrorPreview() {
  MBookingTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      MovieSliderItem(
        movie = mockedMovie,
        genres = AsyncState.Error("Error"),
        onClick = {},
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
      )
    }
  }
}
