package com.skylissh.mbooking.ui.composables.blocks

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import com.composables.icons.lucide.ImageOff
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Star
import com.skylissh.mbooking.BuildConfig
import com.skylissh.mbooking.R
import com.skylissh.mbooking.data.models.Genres
import com.skylissh.mbooking.data.models.Movie
import com.skylissh.mbooking.data.models.genres
import com.skylissh.mbooking.ui.composables.core.Skeleton
import com.skylissh.mbooking.ui.mockedMovie
import com.skylissh.mbooking.ui.mockedMovieGenres
import com.skylissh.mbooking.ui.theme.MBookingTheme
import com.skylissh.mbooking.ui.viewmodels.AsyncState
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter

@Composable
fun MovieCarouselItem(
  movie: Movie,
  genres: AsyncState<Genres>,
  onClick: (Int) -> Unit,
  modifier: Modifier = Modifier,
  offset: Float = 0f,
) {
  val locale = LocalConfiguration.current.locales.get(0)
  val releasedDate = remember {
    if (movie.releaseDate == null) return@remember "---"
    DateTimeFormatter.ofPattern("dd MMM, yyyy").format(movie.releaseDate.toJavaLocalDate())
  }

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .graphicsLayer {
        lerp(start = 0.85f, stop = 1f, fraction = 1f - offset.coerceIn(0f, 1f)).also {
          scaleX = it
          scaleY = it
        }
        alpha = lerp(start = 0.5f, stop = 1f, fraction = 1f - offset.coerceIn(0f, 1f))
      }
  ) {
    Card(
      shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_2xl)),
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface
      ),
      onClick = { onClick(movie.id) },
      modifier = Modifier
        .height(400.dp)
        .fillMaxWidth()
    ) {
      if (movie.posterPath != null) {
        AsyncImage(
          model = ImageRequest.Builder(LocalContext.current)
            .data("${BuildConfig.TMDB_API_IMAGE_URL}${movie.posterPath}")
            .crossfade(true)
            .scale(Scale.FILL)
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
      style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
      maxLines = 1,
      overflow = TextOverflow.Ellipsis
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
    when (genres) {
      is AsyncState.Success -> {
        Text(
          text = "$releasedDate - ${movie.genres(genres.data.genres).joinToString(", ")}",
          style = MaterialTheme.typography.labelLarge,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }

      else -> {
        Skeleton(
          modifier = Modifier
            .height(dimensionResource(R.dimen.padding_xl))
            .width(150.dp)
            .clip(CircleShape)
        )
      }
    }
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
      Icon(
        imageVector = Lucide.Star,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(dimensionResource(R.dimen.icon_small))
      )
      Text(
        text = String.format(locale, "%.2f", movie.voteAverage),
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.SemiBold
      )
      Text(text = "(${movie.voteCount})", style = MaterialTheme.typography.labelSmall)
    }
  }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieCarouselItemPreview() {
  MBookingTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      MovieCarouselItem(
        movie = mockedMovie,
        genres = AsyncState.Success(mockedMovieGenres),
        onClick = {},
        modifier = Modifier.padding(
          horizontal = dimensionResource(R.dimen.padding_xl),
          vertical = dimensionResource(R.dimen.padding_medium)
        )
      )
    }
  }
}
