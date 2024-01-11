package com.example.assignment.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.assignment.data.common.Navigation
import com.example.assignment.presentation.ui.home.HomeScreenPortrait
import com.example.assignment.presentation.ui.home.HomeViewModel
import com.example.assignment.presentation.ui.info.InfoScreenPortrait
import com.example.assignment.presentation.ui.info.InfoViewModel
import com.example.assignment.presentation.ui.theme.AssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AssignmentTheme {
                Navigation()
            }
        }
    }
}

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Navigation.HomeScreen.route) {
        composable(route = Navigation.HomeScreen.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreenPortrait(
                homeViewModel = homeViewModel,
                onUserClicked = { navController.navigate(Navigation.InfoScreen.route + "/$it") })
        }
        composable(
            route = Navigation.InfoScreen.route + "/{login}",
            arguments = listOf(navArgument("login") { type = NavType.StringType })
        ) {
            val infoViewModel: InfoViewModel = hiltViewModel()
            InfoScreenPortrait(
                infoViewModel = infoViewModel,
                login = it.arguments?.getString("login") ?: "",
                onBackClicked = { navController.navigateUp() }
            )
        }
    }
}

