package com.skylissh.mbooking.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skylissh.mbooking.data.models.Genres
import com.skylissh.mbooking.data.network.GenresRepository
import io.ktor.client.plugins.ResponseException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.io.IOException

class GenreViewModel(private val repository: GenresRepository) : ViewModel() {
  private val _movieGenres = MutableStateFlow<AsyncState<Genres>>(AsyncState.Loading)
  val movieGenres: StateFlow<AsyncState<Genres>> = _movieGenres.asStateFlow()

  private val _tvGenres = MutableStateFlow<AsyncState<Genres>>(AsyncState.Loading)
  val tvGenres: StateFlow<AsyncState<Genres>> = _tvGenres.asStateFlow()

  init {
    getMovieGenres()
    getTVGenres()
  }

  fun getMovieGenres() = viewModelScope.launch {
    if (_movieGenres.value is AsyncState.Success) return@launch

    _movieGenres.value = AsyncState.Loading
    _movieGenres.value = try {
      val genres = repository.getMovieGenres()
      AsyncState.Success(genres)
    } catch (e: IOException) {
      AsyncState.Error(e.message ?: "No internet connection")
    } catch (e: ResponseException) {
      AsyncState.Error(e.message ?: "Unknown error")
    }
  }

  fun getTVGenres() = viewModelScope.launch {
    if (_tvGenres.value is AsyncState.Success) return@launch

    _tvGenres.value = AsyncState.Loading
    _tvGenres.value = try {
      val genres = repository.getTVGenres()
      AsyncState.Success(genres)
    } catch (e: IOException) {
      AsyncState.Error(e.message ?: "No internet connection")
    } catch (e: ResponseException) {
      AsyncState.Error(e.message ?: "Unknown error")
    }
  }
}
