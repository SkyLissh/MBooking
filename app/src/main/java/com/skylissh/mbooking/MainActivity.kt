package com.skylissh.mbooking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.skylissh.mbooking.ui.screens.HomeRoute
import com.skylissh.mbooking.ui.screens.HomeScreen
import com.skylissh.mbooking.ui.screens.MovieDetailRoute
import com.skylissh.mbooking.ui.screens.MovieDetailScreen
import com.skylissh.mbooking.ui.screens.SearchRoute
import com.skylissh.mbooking.ui.screens.SearchScreen
import com.skylissh.mbooking.ui.theme.MBookingTheme
import org.koin.compose.KoinApplication

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MBookingTheme {
        KoinApplication(application = { modules(koinModules) }) {
          val navController = rememberNavController()

          NavHost(
            navController = navController,
            startDestination = HomeRoute
          ) {
            composable<HomeRoute> {
              HomeScreen(
                onGoToMovieDetail = { id ->
                  navController.navigate(MovieDetailRoute(id)) {
                    launchSingleTop = true
                    restoreState = true
                  }
                },
                onSearch = { query ->
                  navController.navigate(SearchRoute(query)) {
                    launchSingleTop = true
                    restoreState = true
                  }
                }
              )
            }

            composable<MovieDetailRoute> {
              MovieDetailScreen(onGoBack = { navController.popBackStack() })
            }

            composable<SearchRoute> { backStackEntry ->
              val route: SearchRoute = backStackEntry.toRoute()

              SearchScreen(
                initialQuery = route.query ?: "",
                onGoBack = { navController.popBackStack() },
                onGoToMovieDetail = { id ->
                  navController.navigate(MovieDetailRoute(id)) {
                    launchSingleTop = true
                    restoreState = true
                  }
                }
              )
            }
          }
        }
      }
    }
  }
}
