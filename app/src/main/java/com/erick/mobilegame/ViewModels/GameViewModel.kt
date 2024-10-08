package com.erick.mobilegame.ViewModels

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.erick.mobilegame.Database.WordDao
import com.erick.mobilegame.Database.WordDatabase
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val wordDao: WordDao = WordDatabase.getDatabase(application).wordDao()

    private val _guesses = MutableLiveData<List<String>>(emptyList())
    val guesses: LiveData<List<String>> = _guesses

    private val _currentGuess = MutableLiveData<String>("")
    val currentGuess: LiveData<String> = _currentGuess

    private val _isGuessSubmitted = MutableLiveData<Boolean>(false)
    val isGuessSubmitted: LiveData<Boolean> = _isGuessSubmitted

    private val _keyboardState = MutableLiveData<MutableMap<Char, Color>>(
        mutableMapOf<Char, Color>().apply {
            ('A'..'Z').forEach { put(it, Color.LightGray) }
        }
    )
    val keyboardState: MutableLiveData<MutableMap<Char, Color>> = _keyboardState

    private val _wordToGuess = MutableLiveData<String>()
    val wordToGuess: LiveData<String> = _wordToGuess

    private val _isWordGuessed = MutableLiveData<Boolean>(false)
    val isWordGuessed: LiveData<Boolean> = _isWordGuessed

    private val _isGameOver = MutableLiveData<Boolean>(false)
    val isGameOver: LiveData<Boolean> = _isGameOver

    private var attempts = 0
    private val maxAttempts = 6

    init {
        getRandomWordFromDatabase()
    }

    private fun getRandomWordFromDatabase() {
        viewModelScope.launch {
            val randomWord = wordDao.getRandomWord()
            randomWord?.let {
                _wordToGuess.value = it.word.uppercase()
                println("Palabra seleccionada: ${it.word.uppercase()}")
            }
        }
    }

    fun addLetterToCurrentGuess(letter: Char) {
        val current = _currentGuess.value ?: ""
        if (current.length < 5) {
            _currentGuess.value = current + letter
            _isGuessSubmitted.value = false
        }
    }

    fun getLetterColor(letter: Char, index: Int, wordToGuess: String): Color {
        return when {
            wordToGuess[index] == letter -> Color.Green
            wordToGuess.contains(letter) -> Color.Yellow
            else -> Color.Gray
        }
    }

    fun removeLastLetterFromGuess() {
        val current = _currentGuess.value ?: ""
        if (current.isNotEmpty()) {
            _currentGuess.value = current.dropLast(1)
        }
    }

    fun submitGuess() {
        val guess = _currentGuess.value ?: return
        if (guess.length == 5) {
            _guesses.value = _guesses.value.orEmpty() + guess
            _currentGuess.value = ""
            _isGuessSubmitted.value = true
            updateKeyboardState(guess)

            if (guess == _wordToGuess.value) {
                _isWordGuessed.value = true
            } else {
                attempts++
                if (attempts >= maxAttempts) {
                    _isGameOver.value = true
                }
            }
        }
    }

    private fun updateKeyboardState(guess: String) {
        val newKeyboardState = _keyboardState.value ?: return
        val word = _wordToGuess.value ?: return

        guess.forEachIndexed { index, letter ->
            val currentColor = newKeyboardState[letter] ?: Color.LightGray
            val newColor = getLetterColor(letter, index, word)

            if (currentColor != Color.Green) {
                newKeyboardState[letter] = newColor
            }
        }

        _keyboardState.value = newKeyboardState
    }

    fun resetGame() {

        _guesses.value = emptyList()
        _currentGuess.value = ""
        _isGuessSubmitted.value = false
        _keyboardState.value = mutableMapOf<Char, Color>().apply {
            ('A'..'Z').forEach { put(it, Color.LightGray) }
        }
        _isWordGuessed.value = false
        _isGameOver.value = false
        attempts = 0
        getRandomWordFromDatabase()
    }
}



