package com.example.mathia.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mathia.screen.components.AppScreen
import com.example.mathia.screen.components.InfoDialog
import com.example.mathia.screen.components.OperationButton
import com.example.mathia.ui.theme.*

@Composable
fun HomeScreen(navController: NavHostController) {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        InfoDialog(onDismiss = { showDialog = false })
    }

    AppScreen(
        navController = navController,
        showHomeButton = false,
        onInfoClick = { showDialog = false }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "MathIA",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Choisis une opération et réponds à 5 calculs en dessinant ta réponse !",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Quelle operation veux-tu pratiquer ?",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OperationButton(
                    symbol = "+",
                    label = "Addition",
                    color = AdditionColor,
                    onClick = { }
                )
                OperationButton(
                    symbol = "-",
                    label = "Soustraction",
                    color = SubtractionColor,
                    onClick = { }
                )
                OperationButton(
                    symbol = "×",
                    label = "Multiplication",
                    color = MultiplyColor,
                    onClick = { }
                )
                OperationButton(
                    symbol = "÷",
                    label = "Division",
                    color = DivisionColor,
                    onClick = { }
                )
            }
        }
    }
}