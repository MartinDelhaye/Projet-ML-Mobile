package com.example.mathia.screen.game

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathia.model.Game
import com.example.mathia.model.GameGenerator
import com.example.mathia.model.Operation
import com.example.mathia.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class GameViewModel : ViewModel() {

    private val _game = MutableStateFlow<Game?>(null)
    val game: StateFlow<Game?> = _game

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex

    private val _detectedDigit = MutableStateFlow<String?>(null)
    val detectedDigit: StateFlow<String?> = _detectedDigit

    val questionProgress: StateFlow<String> = combine(
        _currentQuestionIndex
    ) { index ->
        "${index[0] + 1}/5"
    }.stateIn(viewModelScope, SharingStarted.Lazily, "1/5")

    private val _isGameFinished = MutableStateFlow(false)
    val isGameFinished: StateFlow<Boolean> = _isGameFinished

    fun startGame(operation: Operation) {
        _game.value = GameGenerator.generateGame(operation)
        _currentQuestionIndex.value = 0
        _detectedDigit.value = null
        _isGameFinished.value = false
    }

    fun setDetectedDigit(digit: String) {
        _detectedDigit.value = digit
    }

    fun nextQuestion() {
        val currentIndex = _currentQuestionIndex.value
        if (currentIndex >= 4) {
            _isGameFinished.value = true
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

        question.isAnswered = true
        question.isCorrect = _detectedDigit.value?.toIntOrNull() == question.correctAnswer
        question.userProposition = _detectedDigit.value?.toIntOrNull() ?: -1
    }

    fun sendImageToApi(bitmap: Bitmap) {
        viewModelScope.launch {
            try {
                Log.d("API", "Bitmap size: ${bitmap.width}x${bitmap.height}")

                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()

                val requestBody = byteArray.toRequestBody("image/png".toMediaType())
                val part = MultipartBody.Part.createFormData("file", "drawing.png", requestBody)

                val response = RetrofitInstance.api.predict(part)
                Log.d("API", "Response: $response")

                if (response.isNotEmpty()) {
                    var detectedDigitPrepa = ""
                    for (prediction in response) {
                        detectedDigitPrepa += prediction.predicted_class
                    }
                    _detectedDigit.value = detectedDigitPrepa
                }
            } catch (e: Exception) {
                Log.e("API", "Erreur: ${e.message}")
            }
        }
    }
}