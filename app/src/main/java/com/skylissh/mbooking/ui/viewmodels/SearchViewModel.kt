package com.skylissh.mbooking.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.skylissh.mbooking.data.models.Movie
import com.skylissh.mbooking.data.models.Paginated
import com.skylissh.mbooking.data.network.SearchRepository
import com.skylissh.mbooking.ui.screens.SearchRoute
import io.ktor.client.plugins.ResponseException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.io.IOException


data class SearchState(
  val state: AsyncState<Paginated<Movie>> = AsyncState.Loading,
  val hasNextPage: Boolean = true,
  val results: List<Movie> = emptyList(),
)

class SearchViewModel(
  private val repository: SearchRepository,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private val _route: SearchRoute = savedStateHandle.toRoute()

  private var _searchState = MutableStateFlow<SearchState>(SearchState())
  val searchState = _searchState.asStateFlow()

  private val _page = MutableStateFlow(1)

  init {
    val query = _route.query
    if (query != null && query.isNotEmpty()) search(_route.query)
  }

  fun search(query: String) = viewModelScope.launch {
    _page.value = 1
    _searchState.value = SearchState()
    fetchMovies(query)
  }

  fun loadNextPage(query: String) = viewModelScope.launch {
    val (currentState, hasNextPage) = searchState.value
    if (currentState is AsyncState.Loading) return@launch
    if (currentState is AsyncState.Success && !hasNextPage) return@launch

    _page.value++
    fetchMovies(query)
  }

  private fun fetchMovies(query: String) = viewModelScope.launch {
    _searchState.value = searchState.value.copy(state = AsyncState.Loading)
    _searchState.value = try {
      val movies = repository.searchMovies(query, page = _page.value)

      if (movies.results.isEmpty()) {
       return SearchState(state = AsyncState.Error("No movies found"))
      }

      _searchState.value.copy(
        state = AsyncState.Success(movies),
        hasNextPage = movies.totalPages > _page.value,
        results = _searchState.value.results + movies.results
      )
    } catch (e: IOException) {
      _searchState.value.copy(
        state = AsyncState.Error(e.message ?: "No internet connection")
      )
    } catch (e: ResponseException) {
      _searchState.value.copy(
        state = AsyncState.Error(e.message ?: "Unknown error")
      )
    }
  }
}
