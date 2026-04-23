package com.example.mathia.model

enum class Operation {
    ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION
}

data class Game(
    val operation: Operation,
    val questions: List<Question>
)