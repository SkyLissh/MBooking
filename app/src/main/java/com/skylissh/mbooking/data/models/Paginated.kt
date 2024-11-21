package com.skylissh.mbooking.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Paginated<T>(
  val page: Int = 0,
  val totalPages: Int = 0,
  val totalResults: Int = 0,
  val results: List<T>,
)
