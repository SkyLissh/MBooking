package com.skylissh.mbooking.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompany(
  val id: Int = 0,
  @SerialName("logo_path")
  val logoPath: String?,
  val name: String,
  @SerialName("origin_country")
  val originCountry: String,
)
