package com.example.mathia.screen.home

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.mathia.navigation.Screen
import com.example.mathia.screen.components.AppScreen

@Composable
fun HomeScreen(navController: NavHostController) {
    AppScreen(
        navController = navController,
        showHomeButton = false
    ) {
        Text(
            style = MaterialTheme.typography.titleLarge,
            text = "Accueil"
        )
        Text(
            text = "Bienvenue sur l'application MathIA"
        )

        Button(
            onClick = {
                // navController.navigate(Screen.GameScreen.route)
            }
        ) {
            Text(text = "Jouer")
        }
        }
}