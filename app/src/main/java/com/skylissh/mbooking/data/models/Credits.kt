package com.skylissh.mbooking.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Credits(
  val id: Int = 0,
  val cast: List<Cast>,
  val crew: List<Crew>,
)
