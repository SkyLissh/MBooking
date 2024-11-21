package com.skylissh.mbooking.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skylissh.mbooking.data.models.Movie
import com.skylissh.mbooking.data.models.Paginated
import com.skylissh.mbooking.data.network.MovieRepository
import io.ktor.client.plugins.ResponseException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.io.IOException

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {
  private val _nowPlaying =
    MutableStateFlow<AsyncState<Paginated<Movie>>>(AsyncState.Loading)
  val nowPlaying: StateFlow<AsyncState<Paginated<Movie>>> = _nowPlaying.asStateFlow()

  private val _upcoming =
    MutableStateFlow<AsyncState<Paginated<Movie>>>(AsyncState.Loading)
  val upcoming: StateFlow<AsyncState<Paginated<Movie>>> = _upcoming.asStateFlow()

  private val _popular =
    MutableStateFlow<AsyncState<Paginated<Movie>>>(AsyncState.Loading)
  val popular: StateFlow<AsyncState<Paginated<Movie>>> = _popular.asStateFlow()

  private val _topRated =
    MutableStateFlow<AsyncState<Paginated<Movie>>>(AsyncState.Loading)
  val topRated: StateFlow<AsyncState<Paginated<Movie>>> = _topRated.asStateFlow()

  init {
    getNowPlaying()
    getUpcoming()
    getPopular()
    getTopRated()
  }

  fun getNowPlaying() = viewModelScope.launch {
    _nowPlaying.value = AsyncState.Loading
    _nowPlaying.value = try {
      val movies = repository.getNowPlayingMovies()
      AsyncState.Success(movies)
    } catch (e: IOException) {
      AsyncState.Error(e.message ?: "Unknown error")
    } catch (e: ResponseException) {
      AsyncState.Error(e.message ?: "Unknown error")
    }
  }

  fun getUpcoming() = viewModelScope.launch {
    _upcoming.value = AsyncState.Loading
    _upcoming.value = try {
      val movies = repository.getUpcomingMovies()
      AsyncState.Success(movies)
    } catch (e: IOException) {
      AsyncState.Error(e.message ?: "Unknown error")
    } catch (e: ResponseException) {
      AsyncState.Error(e.message ?: "Unknown error")
    }
  }

  fun getPopular() = viewModelScope.launch {
    _popular.value = AsyncState.Loading
    _popular.value = try {
      val movies = repository.getPopularMovies()
      AsyncState.Success(movies)
    } catch (e: IOException) {
      AsyncState.Error(e.message ?: "Unknown error")
    } catch (e: ResponseException) {
      AsyncState.Error(e.message ?: "Unknown error")
    }
  }

  fun getTopRated() = viewModelScope.launch {
    _topRated.value = AsyncState.Loading
    _topRated.value = try {
      val movies = repository.getTopRatedMovies()
      AsyncState.Success(movies)
    } catch (e: IOException) {
      AsyncState.Error(e.message ?: "Unknown error")
    } catch (e: ResponseException) {
      AsyncState.Error(e.message ?: "Unknown error")
    }
  }
}
