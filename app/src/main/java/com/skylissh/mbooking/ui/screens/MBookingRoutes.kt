package com.skylissh.mbooking.ui.screens

import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data class MovieDetailRoute(val id: Int)

@Serializable
data class SearchRoute(val query: String? = "")
