package com.skylissh.mbooking.data.network

import com.skylissh.mbooking.data.models.Movie
import com.skylissh.mbooking.data.models.Paginated
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepository(private val client: HttpClient) {
  suspend fun searchMovies(
    query: String,
    page: Int = 1,
    language: String = "en-Us",
  ): Paginated<Movie> =
    withContext(Dispatchers.IO) {
      val res = client.get(TMDBSearch.Movie(query = query, page = page, language = language))
      res.body()
    }
}
