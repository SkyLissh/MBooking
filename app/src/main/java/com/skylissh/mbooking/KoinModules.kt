package com.skylissh.mbooking

import com.skylissh.mbooking.data.network.GenresRepository
import com.skylissh.mbooking.data.network.MovieRepository
import com.skylissh.mbooking.data.network.SearchRepository
import com.skylissh.mbooking.ui.viewmodels.GenreViewModel
import com.skylissh.mbooking.ui.viewmodels.MovieDetailViewModel
import com.skylissh.mbooking.ui.viewmodels.MovieViewModel
import com.skylissh.mbooking.ui.viewmodels.SearchViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.plugin
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.bearerAuth
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun provideTMDBClient(): HttpClient {
  val client = HttpClient(CIO) {
    install(Resources)
    install(ContentNegotiation) {
      json(Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
      })
    }
    defaultRequest {
      url(BuildConfig.TMDB_API_URL)
    }
  }

  client.plugin(HttpSend).intercept { req ->
    req.bearerAuth(BuildConfig.TMDB_API_TOKEN)

    execute(req)
  }

  return client
}

val networkModule = module {
  singleOf(::provideTMDBClient)
}

val repositoryModule = module {
  factoryOf(::MovieRepository)
  factoryOf(::GenresRepository)
  factoryOf(::SearchRepository)
}

val viewModelModule = module {
  viewModelOf(::MovieViewModel)
  viewModelOf(::GenreViewModel)
  viewModelOf(::MovieDetailViewModel)
  viewModelOf(::SearchViewModel)
}

val koinModules = listOf(networkModule, repositoryModule, viewModelModule)
