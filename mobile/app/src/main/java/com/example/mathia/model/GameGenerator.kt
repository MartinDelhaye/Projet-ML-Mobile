package com.example.mathia.model

object GameGenerator {

    fun generateGame(operation: Operation): Game {
        val allCombinations = generateAllCombinations(operation)
        val selectedQuestions = allCombinations.shuffled().take(5)
        return Game(operation = operation, questions = selectedQuestions)
    }

    private fun generateAllCombinations(operation: Operation): List<Question> {
        val combinations = mutableListOf<Question>()

        when (operation) {
            Operation.ADDITION -> {
                for (a in 0..10) {
                    for (b in 0..10) {
                        combinations.add(
                            Question(
                                questionText = "$a + $b",
                                correctAnswer = a + b
                            )
                        )
                    }
                }
            }
            Operation.SUBTRACTION ->{
                for (a in 1 .. 10){
                    for (b in 0 .. a){
                        combinations.add(
                            Question(
                                questionText = "$a - $b",
                                correctAnswer = a - b
                            )
                        )
                    }
                    }
            }
            Operation.MULTIPLICATION -> {
                for (a in 0 .. 10){
                    for (b in 0 .. 10) {
                        combinations.add(
                            Question(
                                questionText = "$a * $b",
                                correctAnswer = a * b
                            )
                        )
                    }
                }
            }
            Operation.DIVISION -> {
                for (a in 0 .. 10){
                    for (b in 2 .. a){
                        if (a % b == 0){
                            combinations.add(
                                Question(
                                    questionText = "$a / $b",
                                    correctAnswer = a / b
                                )
                            )
                        }
                    }
                }
            }
            else -> {}
        }

        return combinations
    }
}