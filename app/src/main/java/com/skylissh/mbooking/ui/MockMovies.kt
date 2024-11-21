package com.skylissh.mbooking.ui

import com.skylissh.mbooking.data.models.Cast
import com.skylissh.mbooking.data.models.Credits
import com.skylissh.mbooking.data.models.Crew
import com.skylissh.mbooking.data.models.Genre
import com.skylissh.mbooking.data.models.Genres
import com.skylissh.mbooking.data.models.Movie
import com.skylissh.mbooking.data.models.MovieDetail
import kotlinx.datetime.LocalDate

val mockedMovie = Movie(
  id = 1,
  title = "Movie Title - The Second Part",
  overview = "Overview",
  releaseDate = LocalDate.parse("2022-12-12"),
  backdropPath = "https://picsum.photos/id/10/200/300",
  genreIds = List(5) { it },
  originalLanguage = "en",
  originalTitle = "Original Title",
  posterPath = "https://picsum.photos/id/10/200/300",
  voteAverage = 4.567,
  voteCount = 450
)

val mockedMovies = List(5) { mockedMovie }

val mockedMovieGenres = Genres(
  genres = List(5) { Genre(id = it, name = "Genre $it") }
)

val mockedMovieDetail = MovieDetail(
  adult = true,
  backdropPath = "https://picsum.photos/id/10/200/300",
  budget = 4567,
  genres = List(5) { Genre(id = it, name = "Genre $it") },
  homepage = "https://www.google.com",
  id = 1,
  originalLanguage = "en",
  overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.".take(
    100
  ),
  popularity = 0.4567,
  posterPath = "https://picsum.photos/id/10/200/300",
  productionCompanies = emptyList(),
  releaseDate = LocalDate.parse("2022-12-12"),
  revenue = 4567,
  runtime = 120,
  title = "Movie Title - The Second Part",
  video = true,
  voteAverage = 4.567,
  voteCount = 450
)

val mockedCast = Cast(
  adult = true,
  gender = 0,
  id = 1,
  knownForDepartment = "Acting",
  name = "John Doe",
  profilePath = "https://picsum.photos/id/10/200/300",
  character = "John Doe",
)

val mockedCrew = Crew(
  adult = true,
  gender = 0,
  id = 1,
  knownForDepartment = "Acting",
  name = "John Doe",
  profilePath = "https://picsum.photos/id/10/200/300",
  department = "Acting",
  job = "Actor",
)

val mockedCredits = Credits(
  id = 1,
  cast = List(5) { mockedCast },
  crew = List(5) { mockedCrew },
)
