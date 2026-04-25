package com.example.mathia.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import com.example.mathia.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.mathia.model.Question
import com.example.mathia.ui.theme.Error
import com.example.mathia.ui.theme.Success

@Composable
fun CardQuestionResultScreen(
    question: Question
) {
    val backgroundColor = if (question.isCorrect) {
        Success.copy(alpha = 0.15f)
    } else {
        Error.copy(alpha = 0.15f)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row{
                Text(
                    text = "${question.questionText} = ",
                    style = MaterialTheme.typography.bodyLarge
                )
                if (question.isCorrect) {
                    Text(
                        text = "${question.userProposition}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    Text(
                        text = "${question.userProposition ?: "?"}",
                        style = MaterialTheme.typography.bodyLarge,
                        textDecoration = TextDecoration.LineThrough,
                        color = Error
                    )
                }
            }

            if (!question.isCorrect) {
                Text(
                    text = "Réponse : ${question.correctAnswer}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Image(
                painter = painterResource(
                    id = if (question.isCorrect) R.drawable.icon_correct
                    else R.drawable.icon_wrong
                ),
                contentDescription = if (question.isCorrect) "Correct" else "Incorrect",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}