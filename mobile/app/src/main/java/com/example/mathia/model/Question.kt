package com.example.mathia.model

data class Question(
    val questionText: String,
    val correctAnswer: Int,
    var userProposition: Int? = null,
    var isAnswered: Boolean = false,
    var isCorrect: Boolean = false
)