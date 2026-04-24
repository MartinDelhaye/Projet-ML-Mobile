package com.example.mathia.navigation

sealed class Screen(val route: String) {
    data object HomeScreen : Screen(route = "home")
    data object GameScreen : Screen(route = "game/{operation}"){
        fun createRoute(operation: String) = "game/$operation"
    }
    data object ResultScreen : Screen(route = "result")
}