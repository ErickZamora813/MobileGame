package com.erick.mobilegame.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erick.mobilegame.Screens.Components.Keyboard
import com.erick.mobilegame.Screens.Components.WordRow
import com.erick.mobilegame.ViewModels.GameViewModel

@Composable
fun GameScreen(viewModel: GameViewModel) {
    val guesses by viewModel.guesses.observeAsState(emptyList())
    val currentGuess by viewModel.currentGuess.observeAsState("")
    val isGuessSubmitted by viewModel.isGuessSubmitted.observeAsState(false)
    val keyboardState by viewModel.keyboardState.observeAsState(emptyMap())
    val wordToGuess by viewModel.wordToGuess.observeAsState("")
    val isWordGuessed by viewModel.isWordGuessed.observeAsState(false)
    val isGameOver by viewModel.isGameOver.observeAsState(false)

    val maxGuesses = 6
    val wordLength = 5


    if (isWordGuessed) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(text = "¡Felicidades!") },
            text = { Text(text = "Has adivinado la palabra correcta.") },
            confirmButton = {
                Button(onClick = {
                    viewModel.resetGame()
                }) {
                    Text("Volver a jugar")
                }
            }
        )
    }

    if (isGameOver) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(text = "¡Fin del juego!") },
            text = { Text(text = "Lo siento, no has adivinado la palabra. El juego ha terminado.") },
            confirmButton = {
                Button(onClick = {
                    viewModel.resetGame()
                }) {
                    Text("Reiniciar Juego")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (index in 0 until maxGuesses) {
                when {
                    index < guesses.size -> {
                        WordRow(
                            guess = guesses[index],
                            wordToGuess = wordToGuess,
                            showColors = true,
                            viewModel = viewModel
                        )
                    }

                    index == guesses.size -> {
                        WordRow(
                            guess = currentGuess.padEnd(wordLength),
                            wordToGuess = wordToGuess,
                            showColors = isGuessSubmitted,
                            viewModel = viewModel
                        )
                    }

                    else -> {
                        WordRow(
                            guess = "".padEnd(wordLength),
                            wordToGuess = wordToGuess,
                            showColors = false,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Keyboard(
            onKeyPressed = { letter -> viewModel.addLetterToCurrentGuess(letter) },
            onDelete = { viewModel.removeLastLetterFromGuess() },
            onEnter = { viewModel.submitGuess() },
            keyboardState = keyboardState
        )
    }
}


