package com.skylissh.mbooking.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.composables.core.Icon
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import com.skylissh.mbooking.R
import com.skylissh.mbooking.koinModules
import com.skylissh.mbooking.ui.composables.blocks.HomeSectionHeader
import com.skylissh.mbooking.ui.composables.blocks.MovieCarousel
import com.skylissh.mbooking.ui.composables.blocks.MovieSlider
import com.skylissh.mbooking.ui.composables.core.Input
import com.skylissh.mbooking.ui.theme.MBookingTheme
import com.skylissh.mbooking.ui.viewmodels.GenreViewModel
import com.skylissh.mbooking.ui.viewmodels.MovieViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@Composable
fun HomeScreen(
  onGoToMovieDetail: (Int) -> Unit,
  onSearch: (String) -> Unit,
  modifier: Modifier = Modifier,
  movieViewModel: MovieViewModel = koinViewModel(),
  genreViewModel: GenreViewModel = koinViewModel(),
) {
  var search by remember { mutableStateOf("") }

  val nowPlaying by movieViewModel.nowPlaying.collectAsState()
  val upcoming by movieViewModel.upcoming.collectAsState()
  val popular by movieViewModel.popular.collectAsState()
  val topRated by movieViewModel.topRated.collectAsState()
  val movieGenres by genreViewModel.movieGenres.collectAsState()

  Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
    Column(
      modifier = Modifier
        .padding(innerPadding)
        .padding(vertical = dimensionResource(R.dimen.padding_medium))
        .verticalScroll(rememberScrollState())
    ) {
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = dimensionResource(R.dimen.padding_medium))
      ) {
        Column {
          Text(
            text = stringResource(R.string.greeting, "Android"),
            style = MaterialTheme.typography.bodyLarge
          )
          Text(
            text = stringResource(R.string.welcome_back),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
          )
        }
        IconButton(
          onClick = {/*TODO*/ },
        ) {
          Icon(
            imageVector = Lucide.Bell,
            contentDescription = stringResource(R.string.notifications),
            tint = MaterialTheme.colorScheme.primary
          )
        }
      }
      Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
      Input(
        value = search,
        onValueChange = { search = it },
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = dimensionResource(R.dimen.padding_medium)),
        leadingIcon = {
          Icon(
            imageVector = Lucide.Search,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
          )
        },
        placeholder = { Text(text = stringResource(R.string.search)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch(search) })
      )
      Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
      HomeSectionHeader(title = R.string.now_playing, onClick = { /*TODO*/ })

      Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
      MovieCarousel(
        movies = nowPlaying,
        genres = movieGenres,
        onClickMovie = onGoToMovieDetail
      )
      Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_xl)))

      HomeSectionHeader(title = R.string.coming_soon, onClick = { /*TODO*/ })
      Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
      MovieSlider(movies = upcoming, genres = movieGenres, onClickMovie = onGoToMovieDetail)
      Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

      HomeSectionHeader(title = R.string.popular, onClick = { /*TODO*/ })
      Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
      MovieSlider(movies = popular, genres = movieGenres, onClickMovie = onGoToMovieDetail)
      Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

      HomeSectionHeader(title = R.string.top_rated, onClick = { /*TODO*/ })
      Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
      MovieSlider(movies = topRated, genres = movieGenres, onClickMovie = onGoToMovieDetail)
      Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
    }
  }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GreetingPreview() {
  MBookingTheme {
    KoinApplication({ modules(koinModules) }) {
      HomeScreen(onGoToMovieDetail = {}, onSearch = {})
    }
  }
}
