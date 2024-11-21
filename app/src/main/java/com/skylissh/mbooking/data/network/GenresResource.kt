package com.skylissh.mbooking.data.network

import io.ktor.resources.Resource

@Resource("/genre")
class TMDBGenres {
  @Resource("movie/list")
  data class Movie(val parent: TMDBGenres = TMDBGenres(), val language: String? = "en")

  @Resource("tv/list")
  data class TV(val parent: TMDBGenres = TMDBGenres(), val language: String? = "en")
}
