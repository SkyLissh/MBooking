package com.skylissh.mbooking.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
  val id: Int,
  val name: String,
)

@Serializable
data class Genres(
  val genres: List<Genre>,
)
