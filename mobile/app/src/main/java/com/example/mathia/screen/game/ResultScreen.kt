package com.example.mathia.screen.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mathia.R
import com.example.mathia.navigation.Screen
import com.example.mathia.screen.components.AppScreen
import com.example.mathia.screen.components.CardQuestionResultScreen

@Composable
fun ResultScreen(
    navController: NavHostController,
    viewModel: GameViewModel
) {
    val game by viewModel.game.collectAsState()
    val questions = game?.questions ?: emptyList()
    val score = questions.count { it.isCorrect }

    val message = when (score) {
        5 -> "Parfait ! 🌟"
        4 -> "Très bien !"
        3 -> "Bien joué !"
        2 -> "Pas mal !"
        1 -> "Continue d'essayer !"
        else -> "Dommage !"
    }

    val smileyRes = when (score) {
        5 -> R.drawable.icon_smiley_5_on_5
        4 -> R.drawable.icon_smiley_4_on_5
        3 -> R.drawable.icon_smiley_3_on_5
        2 -> R.drawable.icon_smiley_2_on_5
        1 -> R.drawable.icon_smiley_1_on_5
        else -> R.drawable.icon_smiley_0_on_5
    }

    AppScreen(
        navController = navController,
        showHomeButton = false
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = message,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }

            item {
                Image(
                    painter = painterResource(id = smileyRes),
                    contentDescription = "Score smiley",
                    modifier = Modifier.size(120.dp)
                )
            }

            item {
                Text(
                    text = "$score / 5",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }

            items(questions) { question ->
                CardQuestionResultScreen(question = question)
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = {
                            game?.operation?.let { operation ->
                                viewModel.startGame(operation)
                                navController.navigate(
                                    Screen.GameScreen.createRoute(operation.name)
                                ) {
                                    popUpTo(Screen.ResultScreen.route) { inclusive = true }
                                }
                            }
                        }
                    ) {
                        Text(text = "Rejouer 🔄")
                    }

                    Button(
                        onClick = {
                            viewModel.resetGame()
                            navController.navigate(Screen.HomeScreen.route) {
                                popUpTo(Screen.HomeScreen.route) { inclusive = true }
                            }
                        }
                    ) {
                        Text(text = "Accueil 🏠")
                    }
                }
            }
        }
    }
}