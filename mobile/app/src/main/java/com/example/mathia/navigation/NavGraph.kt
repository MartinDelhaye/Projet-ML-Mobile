package com.example.mathia.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mathia.model.Operation
import com.example.mathia.screen.game.GameScreen
import com.example.mathia.screen.home.HomeScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.GameScreen.route) { backStackEntry ->
            val operation = backStackEntry.arguments?.getString("operation") ?: "ADDITION"
            GameScreen(
                navController = navController,
                operation = Operation.valueOf(operation)
            )
        }
        composable(route = Screen.ResultScreen.route) {
            // ResultScreen(navController = navController)
        }
    }
}