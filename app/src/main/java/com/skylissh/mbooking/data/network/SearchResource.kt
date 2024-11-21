package com.skylissh.mbooking.data.network

import io.ktor.resources.Resource
import kotlinx.serialization.SerialName

@Resource("/search")
class TMDBSearch() {
  @Resource("movie")
  data class Movie(
    val parent: TMDBSearch = TMDBSearch(),
    val query: String,
    val page: Int? = 1,
    val language: String? = "en-US",
    @SerialName("include_adult") val includeAdult: Boolean? = true,
  )
}
