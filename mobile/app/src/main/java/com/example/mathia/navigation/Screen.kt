package com.example.mathia.navigation

sealed class Screen(val route: String) {
    data object HomeScreen : Screen(route = "home")
    data object GameScreen : Screen(route = "game")
    data object ResultScreen : Screen(route = "result")
}