package  com.skylissh.mbooking.data.network

import io.ktor.resources.Resource

@Resource("/movie")
class TMDBMovie {
  @Resource("now_playing")
  data class NowPlaying(
    val parent: TMDBMovie = TMDBMovie(),
    val page: Int? = 1,
    val language: String? = "en-US",
  )

  @Resource("upcoming")
  data class Upcoming(
    val parent: TMDBMovie = TMDBMovie(),
    val page: Int? = 1,
    val language: String? = "en-US",
  )

  @Resource("popular")
  data class Popular(
    val parent: TMDBMovie = TMDBMovie(),
    val page: Int? = 1,
    val language: String? = "en-US",
  )

  @Resource("top_rated")
  data class TopRated(
    val parent: TMDBMovie = TMDBMovie(),
    val page: Int? = 1,
    val language: String? = "en-US",
  )

  @Resource("{movieId}")
  data class Details(
    val parent: TMDBMovie = TMDBMovie(),
    val movieId: Int,
    val language: String? = "en-US",
  ) {
    @Resource("credits")
    data class Credits(val parent: Details, val language: String? = "en-US")
  }
}
