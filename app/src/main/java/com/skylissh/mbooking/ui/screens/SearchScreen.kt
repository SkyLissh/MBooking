package com.skylissh.mbooking.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.core.Icon
import com.composables.icons.lucide.Film
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.VideoOff
import com.skylissh.mbooking.R
import com.skylissh.mbooking.koinModules
import com.skylissh.mbooking.ui.composables.blocks.MovieListItem
import com.skylissh.mbooking.ui.composables.blocks.SearchTopBar
import com.skylissh.mbooking.ui.composables.core.rememberDebounce
import com.skylissh.mbooking.ui.extensions.reachedBottom
import com.skylissh.mbooking.ui.theme.MBookingTheme
import com.skylissh.mbooking.ui.viewmodels.AsyncState
import com.skylissh.mbooking.ui.viewmodels.SearchViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@Composable
fun SearchScreen(
  modifier: Modifier = Modifier,
  initialQuery: String = "",
  searchViewModel: SearchViewModel = koinViewModel(),
  onGoBack: () -> Unit = {},
  onGoToMovieDetail: (Int) -> Unit = {},
) {
  var query by rememberSaveable { mutableStateOf(initialQuery) }

  val search by searchViewModel.searchState.collectAsState()
  val searchDebounce = rememberDebounce<String> { searchViewModel.search(it) }

  val listState = rememberLazyListState()
  val reachedBottom by remember { derivedStateOf { listState.reachedBottom() } }

  LaunchedEffect(reachedBottom) {
    if (reachedBottom) searchViewModel.loadNextPage(query)
  }

  Scaffold(
    modifier = modifier,
    topBar = {
      SearchTopBar(
        value = query,
        onValueChange = { value ->
          query = value
          searchDebounce(value)
        },
        onGoBack = onGoBack
      )
    }
  ) { innerPadding ->
    when {
      search.results.isNotEmpty() -> LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        contentPadding = PaddingValues(horizontal = dimensionResource(R.dimen.padding_medium)),
        modifier = Modifier.padding(innerPadding)
      ) {
        items(search.results) { movie ->
          MovieListItem(movie, onClick = onGoToMovieDetail, modifier = Modifier.fillMaxWidth())
        }
      }

      search.state is AsyncState.Error -> Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
          .padding(innerPadding)
          .padding(dimensionResource(R.dimen.padding_medium))
          .fillMaxSize()
      ) {
        Icon(
          imageVector = Lucide.VideoOff,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.size(84.dp)
        )
        Text(
          stringResource(R.string.movies_not_found),
          style = MaterialTheme.typography.titleMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          textAlign = TextAlign.Center,
        )
      }

      search.state is AsyncState.Loading -> Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
          .padding(innerPadding)
          .padding(dimensionResource(R.dimen.padding_medium))
          .fillMaxSize()
      ) {
        CircularProgressIndicator(modifier = Modifier.size(84.dp))
      }

      else -> Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
          .padding(innerPadding)
          .padding(dimensionResource(R.dimen.padding_medium))
          .fillMaxSize()
      ) {
        Icon(
          imageVector = Lucide.Film,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.size(84.dp)
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        Text(
          stringResource(R.string.movies_search),
          style = MaterialTheme.typography.titleMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          textAlign = TextAlign.Center,
        )
      }
    }
  }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SearchScreenPreview() {
  MBookingTheme {
    KoinApplication({ modules(koinModules) }) {
      SearchScreen()
    }
  }
}
