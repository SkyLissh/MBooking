package com.skylissh.mbooking.data.models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
  val adult: Boolean = false,

  @SerialName("backdrop_path")
  val backdropPath: String?,

  @SerialName("genre_ids")
  val genreIds: List<Int>,

  val id: Int = 0,

  @SerialName("original_language")
  val originalLanguage: String,

  @SerialName("original_title")
  val originalTitle: String,

  val overview: String,

  val popularity: Double = 0.0,

  @SerialName("poster_path")
  val posterPath: String?,

  @SerialName("release_date")
  @Serializable(with = NullableLocalDateSerializer::class)
  val releaseDate: LocalDate?,

  @SerialName("title")
  val title: String,

  @SerialName("video")
  val video: Boolean = false,

  @SerialName("vote_average")
  val voteAverage: Double = 0.0,

  @SerialName("vote_count")
  val voteCount: Int = 0,
)

fun Movie.genres(genres: List<Genre>): List<String> {
  return genreIds.map { id -> genres.find { it.id == id }?.name ?: "" }
}
