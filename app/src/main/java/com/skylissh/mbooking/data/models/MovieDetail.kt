package com.skylissh.mbooking.data.models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetail(
  val adult: Boolean = true,
  @SerialName("backdrop_path")
  val backdropPath: String?,
  val budget: Int = 0,
  val genres: List<Genre>,
  val homepage: String,
  val id: Int = 0,
  @SerialName("original_language")
  val originalLanguage: String,
  val overview: String,
  val popularity: Double = 0.0,
  @SerialName("poster_path")
  val posterPath: String?,
  @SerialName("production_companies")
  val productionCompanies: List<ProductionCompany>,
  @SerialName("release_date")
  @Serializable(with = NullableLocalDateSerializer::class)
  val releaseDate: LocalDate?,
  val revenue: Long = 0,
  val runtime: Int = 0,
  val title: String,
  val video: Boolean = false,
  @SerialName("vote_average")
  val voteAverage: Double = 0.0,
  @SerialName("vote_count")
  val voteCount: Int = 0,
)
