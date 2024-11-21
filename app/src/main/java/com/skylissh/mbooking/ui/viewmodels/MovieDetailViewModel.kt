package com.skylissh.mbooking.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.skylissh.mbooking.data.models.Credits
import com.skylissh.mbooking.data.models.MovieDetail
import com.skylissh.mbooking.data.network.MovieRepository
import com.skylissh.mbooking.ui.screens.MovieDetailRoute
import io.ktor.client.plugins.ResponseException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.io.IOException

class MovieDetailViewModel(
  private val repository: MovieRepository,
  savedSate: SavedStateHandle,
) : ViewModel() {
  private val _route: MovieDetailRoute = savedSate.toRoute()

  private var _details = MutableStateFlow<AsyncState<MovieDetail>>(AsyncState.Loading)
  val details: StateFlow<AsyncState<MovieDetail>> = _details.asStateFlow()

  private var _credits = MutableStateFlow<AsyncState<Credits>>(AsyncState.Loading)
  val credits: StateFlow<AsyncState<Credits>> = _credits.asStateFlow()

  init {
    getDetails(_route.id)
    getCredits(_route.id)
  }

  fun getDetails(movieId: Int) = viewModelScope.launch {
    _details.value = AsyncState.Loading
    _details.value = try {
      val movieDetail = repository.getDetails(movieId)
      AsyncState.Success(movieDetail)
    } catch (e: IOException) {
      AsyncState.Error(e.message ?: "Unknown error")
    } catch (e: ResponseException) {
      AsyncState.Error(e.message ?: "Unknown error")
    }
  }

  fun getCredits(movieId: Int) = viewModelScope.launch {
    _credits.value = AsyncState.Loading
    _credits.value = try {
      val credits = repository.getCredits(movieId)
      AsyncState.Success(credits)
    } catch (e: IOException) {
      AsyncState.Error(e.message ?: "Unknown error")
    } catch (e: ResponseException) {
      AsyncState.Error(e.message ?: "Unknown error")
    }
  }
}
