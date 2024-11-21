package com.skylissh.mbooking.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.composables.core.Icon
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.ImageOff
import com.composables.icons.lucide.Lucide
import com.skylissh.mbooking.BuildConfig
import com.skylissh.mbooking.R
import com.skylissh.mbooking.data.models.Credits
import com.skylissh.mbooking.data.models.MovieDetail
import com.skylissh.mbooking.ui.composables.blocks.CastCard
import com.skylissh.mbooking.ui.composables.blocks.MovieInfoCard
import com.skylissh.mbooking.ui.composables.blocks.SkeletonCastCard
import com.skylissh.mbooking.ui.mockedCredits
import com.skylissh.mbooking.ui.mockedMovieDetail
import com.skylissh.mbooking.ui.theme.MBookingTheme
import com.skylissh.mbooking.ui.viewmodels.AsyncState
import com.skylissh.mbooking.ui.viewmodels.MovieDetailViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.NumberFormat

@Composable
fun MovieDetailScreen(
  modifier: Modifier = Modifier,
  movieDetailViewModel: MovieDetailViewModel = koinViewModel(),
  onGoBack: () -> Unit = {},
) {
  val movieDetail by movieDetailViewModel.details.collectAsState()
  val credits by movieDetailViewModel.credits.collectAsState()

  when (val details = movieDetail) {
    is AsyncState.Loading -> LoadingScreen(modifier)
    is AsyncState.Error -> ErrorScreen(modifier)
    is AsyncState.Success -> MovieDetailScreen(details.data, credits, modifier, onGoBack)
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
  details: MovieDetail,
  credits: AsyncState<Credits>,
  modifier: Modifier = Modifier,
  onGoBack: () -> Unit = {},
) {
  val numberFormat = NumberFormat.getCurrencyInstance()

  Scaffold(
    topBar = {
      TopAppBar(
        title = { },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = Color.Transparent,
        ),
        navigationIcon = {
          IconButton(onClick = onGoBack) {
            Icon(
              imageVector = Lucide.ChevronLeft,
              contentDescription = stringResource(R.string.go_back),
              tint = MaterialTheme.colorScheme.onBackground
            )
          }
        }
      )
    }
  ) { innerPadding ->
    Column(
      verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_xl)),
      modifier = modifier
        .padding(bottom = innerPadding.calculateBottomPadding())
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
    ) {
      Box {
        if (details.posterPath != null) {
          AsyncImage(
            model = "${BuildConfig.TMDB_API_IMAGE_URL}${details.posterPath}",
            contentDescription = stringResource(R.string.movie_poster),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(
              MaterialTheme.colorScheme.background.copy(alpha = 0.4f),
              BlendMode.SrcOver
            ),
            modifier = Modifier
              .fillMaxWidth()
              .height(240.dp)
          )
        } else {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(240.dp)
          ) {
            Icon(
              imageVector = Lucide.ImageOff,
              contentDescription = stringResource(R.string.movie_poster_not_found),
              tint = MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier
                .size(84.dp)
                .align(Alignment.Center)
            )
          }
        }
        MovieInfoCard(
          title = details.title,
          releaseDate = details.releaseDate,
          runtime = details.runtime,
          voteAverage = details.voteAverage,
          voteCount = details.voteCount,
          modifier = Modifier
            .align(Alignment.BottomCenter)
            .offset(y = 60.dp)
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        )
      }
      Spacer(modifier = Modifier.height(40.dp))
      Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
      ) {
        MovieTag(
          tag = stringResource(R.string.genres),
          value = details.genres.map { it.name }.joinToString(", ")
        )
        MovieTag(
          tag = stringResource(R.string.original_language),
          value = details.originalLanguage
        )
        MovieTag(
          tag = stringResource(R.string.revenue),
          value = numberFormat.format(details.revenue)
        )
      }

      Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
      ) {
        Text(
          text = stringResource(R.string.overview),
          style = MaterialTheme.typography.headlineSmall,
          fontWeight = FontWeight.Bold,
        )
        Text(text = details.overview, style = MaterialTheme.typography.bodyLarge)
      }
      Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))) {
        Text(
          text = stringResource(R.string.cast),
          style = MaterialTheme.typography.headlineSmall,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        )
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
          contentPadding = PaddingValues(horizontal = dimensionResource(R.dimen.padding_medium))
        ) {
          if (credits is AsyncState.Success) {
            items(credits.data.cast) { cast ->
              CastCard(name = cast.name, role = cast.character, avatarUrl = cast.profilePath)
            }
          } else {
            items(List(5) { it }) { SkeletonCastCard() }
          }
        }
      }
      Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
      ) {
        Text(
          text = stringResource(R.string.crew),
          style = MaterialTheme.typography.headlineSmall,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        )
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
          contentPadding = PaddingValues(horizontal = dimensionResource(R.dimen.padding_medium))
        ) {
          if (credits is AsyncState.Success) {
            items(credits.data.crew) { crew ->
              CastCard(name = crew.name, role = crew.job, avatarUrl = crew.profilePath)
            }
          } else {
            items(List(5) { it }) { SkeletonCastCard() }
          }
        }
      }
    }
  }
}

@Composable
private fun MovieTag(tag: String, value: String, modifier: Modifier = Modifier) {
  Text(
    text = buildAnnotatedString {
      append(tag)
      append(": ")
      withStyle(
        style = SpanStyle(
          color = MaterialTheme.colorScheme.onBackground,
          fontWeight = FontWeight.Bold
        )
      ) { append(value) }
    },
    style = MaterialTheme.typography.labelLarge,
    fontWeight = FontWeight.Normal,
    color = MaterialTheme.colorScheme.onSurfaceVariant,
    modifier = modifier
  )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MovieDetailScreenPreview() {
  MBookingTheme {
    MovieDetailScreen(details = mockedMovieDetail, credits = AsyncState.Success(mockedCredits))
  }
}
