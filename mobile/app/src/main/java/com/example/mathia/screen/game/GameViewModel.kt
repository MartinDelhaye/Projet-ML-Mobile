package com.example.mathia.screen.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.mathia.model.Game
import com.example.mathia.model.GameGenerator
import com.example.mathia.model.Operation
import com.example.mathia.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class GameViewModel : ViewModel() {

    private val _game = MutableStateFlow<Game?>(null)
    val game: StateFlow<Game?> = _game

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex

    private val _detectedDigit = MutableStateFlow<Int?>(null)
    val detectedDigit: StateFlow<Int?> = _detectedDigit

    val questionProgress: StateFlow<String> = combine(
        _currentQuestionIndex
    ) { index ->
        "${index[0] + 1}/5"
    }.stateIn(viewModelScope, SharingStarted.Lazily, "1/5")

    private val _isGameFinished = MutableStateFlow(false)
    val isGameFinished: StateFlow<Boolean> = _isGameFinished

    fun nextQuestion() {
        val currentIndex = _currentQuestionIndex.value
        if (currentIndex >= 4) {
            _isGameFinished.value = true
        } else {
            _currentQuestionIndex.value = currentIndex + 1
            _detectedDigit.value = null
        }
    }

    fun startGame(operation: Operation) {
        _game.value = GameGenerator.generateGame(operation)
        _currentQuestionIndex.value = 0
        _detectedDigit.value = null
    }

    fun setDetectedDigit(digit: Int) {
        _detectedDigit.value = digit
    }

    fun nextQuestion(navController: NavHostController) {
        val currentIndex = _currentQuestionIndex.value
        if (currentIndex >= 4) {
            navController.navigate(Screen.ResultScreen.route)
        } else {
            _currentQuestionIndex.value = currentIndex + 1
            _detectedDigit.value = null
        }
    }

    fun validateAnswer() {
        val game = _game.value ?: return
        val currentIndex = _currentQuestionIndex.value
        val question = game.questions[currentIndex]
        val detected = _detectedDigit.value ?: return

        question.userProposition = detected
        question.isAnswered = true
        question.isCorrect = detected == question.correctAnswer
    }
}