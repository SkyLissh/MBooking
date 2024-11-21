package com.skylissh.mbooking.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Crew(
  val adult: Boolean = false,
  val gender: Int = 0,
  val id: Int = 0,
  @SerialName("known_for_department")
  val knownForDepartment: String,
  val name: String,
  @SerialName("profile_path")
  val profilePath: String?,
  val department: String,
  val job: String,
)
