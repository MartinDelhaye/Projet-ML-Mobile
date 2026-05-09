package com.example.mathia.screen.game

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mathia.model.Operation
import com.example.mathia.navigation.Screen
import com.example.mathia.screen.components.AppScreen
import com.example.mathia.screen.components.DrawingCanvas

@Composable
fun GameScreen(
    navController: NavHostController,
    operation: Operation,
    viewModel: GameViewModel = viewModel()
) {
    val game by viewModel.game.collectAsState()
    val currentIndex by viewModel.currentQuestionIndex.collectAsState()
    val detectedDigit by viewModel.detectedDigit.collectAsState()
    val isGameFinished by viewModel.isGameFinished.collectAsState()
    var clearTrigger by remember { mutableStateOf(false) }
    var hasDrawn by remember { mutableStateOf(false) }

    // Démarrer la partie
    LaunchedEffect(operation) {
        viewModel.startGame(operation)
    }

    // Navigation vers résultat
    LaunchedEffect(isGameFinished) {
        if (isGameFinished) {
            navController.navigate(Screen.ResultScreen.route)
        }
    }

    val currentQuestion = game?.questions?.getOrNull(currentIndex)

    AppScreen(
        navController = navController,
        showHomeButton = true
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Progress
            Text(
                text = "${currentIndex + 1}/5",
                style = MaterialTheme.typography.titleMedium
            )

            // Question
            Text(
                text = currentQuestion?.questionText ?: "",
                style = MaterialTheme.typography.titleLarge
            )

            // Zone de dessin
            // Zone de dessin
            DrawingCanvas(
                onDrawEnd = { bitmap ->
                    hasDrawn = true
                    viewModel.sendImageToApi(bitmap)
                },
                clearTrigger = clearTrigger,
                modifier = Modifier
                    .size(280.dp)
                    .padding(8.dp)
            )

            // Bouton effacer sur le canvas
            Button(
                onClick = {
                    clearTrigger = !clearTrigger
                    hasDrawn = false
                    viewModel.setDetectedDigit("-1")
                }
            ) {
                Text(text = "Effacer ✏️")
            }

            // Phrase de détection
            if (hasDrawn && detectedDigit != null && detectedDigit != "-1") {
                Text(
                    text = "Tu penses que c'est $detectedDigit ?",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // Boutons action
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        viewModel.validateAnswer()
                        clearTrigger = !clearTrigger
                        hasDrawn = false
                        viewModel.nextQuestion()
                    },
                    enabled = hasDrawn && detectedDigit != null && detectedDigit != "-1"
                ) {
                    Text(text = "C'est ma réponse ! ✅")
                }

                Button(
                    onClick = {
                        clearTrigger = !clearTrigger
                        hasDrawn = false
                        viewModel.nextQuestion()
                    }
                ) {
                    Text(text = "Passer ⏭️")
                }
            }
        }
    }
}