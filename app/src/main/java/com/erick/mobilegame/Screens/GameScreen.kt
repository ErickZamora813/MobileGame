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
    val isWordGuessed by viewModel.isWordGuessed.observeAsState(false) // Observa si la palabra fue adivinada

    // Mostrar diálogo si todos los recuadros son verdes
    if (isWordGuessed) {
        AlertDialog(
            onDismissRequest = { /* Nada */ },
            title = { Text(text = "¡Felicidades!") },
            text = { Text(text = "Has adivinado la palabra correcta.") },
            confirmButton = {
                Button(onClick = {
                    viewModel.resetGame() // Reiniciar el juego
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
        // Contenido del tablero
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            guesses.forEach { guess ->
                WordRow(
                    guess = guess,
                    wordToGuess = wordToGuess,
                    showColors = true,
                    viewModel = viewModel
                )
            }
            WordRow(
                guess = currentGuess.padEnd(5),
                wordToGuess = wordToGuess,
                showColors = isGuessSubmitted,
                viewModel = viewModel
            )
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


