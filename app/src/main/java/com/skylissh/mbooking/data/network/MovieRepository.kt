package com.skylissh.mbooking.data.network

import android.util.Log
import com.skylissh.mbooking.data.models.Credits
import com.skylissh.mbooking.data.models.Movie
import com.skylissh.mbooking.data.models.MovieDetail
import com.skylissh.mbooking.data.models.Paginated
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val client: HttpClient) {
  suspend fun getNowPlayingMovies(
    page: Int = 1,
    language: String = "en-Us",
  ): Paginated<Movie> = withContext(Dispatchers.IO) {
    Log.d("MovieRepository", "${Thread.currentThread().name} getNowPlayingMovies")
    val res = client.get(TMDBMovie.NowPlaying(page = page, language = language))
    res.body()
  }

  suspend fun getUpcomingMovies(
    page: Int = 1,
    language: String = "en-Us",
  ): Paginated<Movie> = withContext(Dispatchers.IO) {
    Log.d("MovieRepository", "${Thread.currentThread().name} getUpcomingMovies")
    val res = client.get(TMDBMovie.Upcoming(page = page, language = language))
    res.body()
  }

  suspend fun getPopularMovies(
    page: Int = 1,
    language: String = "en-Us",
  ): Paginated<Movie> = withContext(Dispatchers.IO) {
    val res = client.get(TMDBMovie.Popular(page = page, language = language))
    res.body()
  }

  suspend fun getTopRatedMovies(
    page: Int = 1,
    language: String = "en-Us",
  ): Paginated<Movie> = withContext(Dispatchers.IO) {
    val res = client.get(TMDBMovie.TopRated(page = page, language = language))
    res.body()
  }

  suspend fun getDetails(movieId: Int, language: String = "en-Us"): MovieDetail =
    withContext(Dispatchers.IO) {
      val res = client.get(TMDBMovie.Details(movieId = movieId, language = language))
      res.body()
    }

  suspend fun getCredits(movieId: Int, language: String = "en-Us"): Credits =
    withContext(Dispatchers.IO) {
      val res = client.get(
        TMDBMovie.Details.Credits(
          TMDBMovie.Details(movieId = movieId),
          language = language
        )
      )
      res.body()
    }
}
