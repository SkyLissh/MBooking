package com.skylissh.mbooking.data.network

import com.skylissh.mbooking.data.models.Genres
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GenresRepository(private val client: HttpClient) {
  suspend fun getMovieGenres(): Genres = withContext(Dispatchers.IO) {
    val res = client.get(TMDBGenres.Movie())
    res.body()
  }

  suspend fun getTVGenres(): Genres = withContext(Dispatchers.IO) {
    val res = client.get(TMDBGenres.TV())
    res.body()
  }
}
