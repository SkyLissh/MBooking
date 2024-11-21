package com.skylissh.mbooking.ui.composables.blocks

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skylissh.mbooking.data.models.Genres
import com.skylissh.mbooking.data.models.Movie
import com.skylissh.mbooking.data.models.Paginated
import com.skylissh.mbooking.ui.composables.core.Carousel
import com.skylissh.mbooking.ui.mockedMovieGenres
import com.skylissh.mbooking.ui.mockedMovies
import com.skylissh.mbooking.ui.theme.MBookingTheme
import com.skylissh.mbooking.ui.viewmodels.AsyncState

@Composable
fun MovieCarousel(
  movies: AsyncState<Paginated<Movie>>,
  genres: AsyncState<Genres>,
  onClickMovie: (Int) -> Unit,
  modifier: Modifier = Modifier,
) {
  when (movies) {
    is AsyncState.Success -> {
      Carousel(items = movies.data.results, modifier = modifier) { movie, offset ->
        MovieCarouselItem(
          movie = movie,
          genres = genres,
          offset = offset,
          onClick = onClickMovie
        )
      }
    }

    else -> {
      Carousel(items = List(5) { it }, modifier = modifier) { index, offset ->
        SkeletonCarouselItem(offset = offset)
      }
    }
  }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieCarouselPreviewLoading() {
  MBookingTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      MovieCarousel(
        movies = AsyncState.Loading,
        genres = AsyncState.Success(mockedMovieGenres),
        onClickMovie = {},
        modifier = Modifier.padding(vertical = 16.dp)
      )
    }
  }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieCarouselPreviewSuccess() {
  MBookingTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      MovieCarousel(
        movies = AsyncState.Success(Paginated(results = mockedMovies)),
        genres = AsyncState.Success(mockedMovieGenres),
        onClickMovie = {},
        modifier = Modifier.padding(vertical = 16.dp)
      )
    }
  }
}
