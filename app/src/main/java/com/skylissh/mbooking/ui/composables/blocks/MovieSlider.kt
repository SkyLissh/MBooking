package com.skylissh.mbooking.ui.composables.blocks

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skylissh.mbooking.R
import com.skylissh.mbooking.data.models.Genres
import com.skylissh.mbooking.data.models.Movie
import com.skylissh.mbooking.data.models.Paginated
import com.skylissh.mbooking.ui.mockedMovieGenres
import com.skylissh.mbooking.ui.mockedMovies
import com.skylissh.mbooking.ui.theme.MBookingTheme
import com.skylissh.mbooking.ui.viewmodels.AsyncState

@Composable
fun MovieSlider(
  movies: AsyncState<Paginated<Movie>>,
  genres: AsyncState<Genres>,
  onClickMovie: (Int) -> Unit,
  modifier: Modifier = Modifier,
) {
  LazyRow(
    horizontalArrangement = Arrangement.spacedBy(dimensionResource((R.dimen.padding_medium))),
    contentPadding = PaddingValues(horizontal = dimensionResource(R.dimen.padding_medium)),
    modifier = modifier
  ) {
    if (movies is AsyncState.Success) {
      items(movies.data.results) {
        MovieSliderItem(
          movie = it,
          genres = genres,
          onClick = onClickMovie
        )
      }
    } else {
      items(List(5) { it }) { SkeletonSliderItem() }
    }
  }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieSliderLoadingPreview() {
  MBookingTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      MovieSlider(
        movies = AsyncState.Loading,
        genres = AsyncState.Loading,
        onClickMovie = {},
        modifier = Modifier.padding(16.dp)
      )
    }
  }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieSliderSuccessPreview() {
  MBookingTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      MovieSlider(
        movies = AsyncState.Success(Paginated(results = mockedMovies)),
        genres = AsyncState.Success(mockedMovieGenres),
        onClickMovie = {},
        modifier = Modifier.padding(16.dp)
      )
    }
  }
}
