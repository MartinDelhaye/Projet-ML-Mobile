package com.example.mathia.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InfoDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "🎓 Bienvenue sur MathIA !",
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Column {
                Text(text = "MathIA est une application de maths conçue pour les enfants.")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Pour les enfants ne sachant pas encore lire, nous recommandons qu'un adulte soit présent pour les aider.")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Bonne chance !")
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(text = "J'ai compris !")
            }
        }
    )
}