package com.example.mathia.screen.home

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.example.mathia.navigation.Screen
import com.example.mathia.screen.components.AppScreen
import com.example.mathia.screen.components.InfoDialog

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        InfoDialog(onDismiss = { showDialog = false })
    }
    AppScreen(
        navController = navController,
        showHomeButton = false,
        onInfoClick = { showDialog = true }
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