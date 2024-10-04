package com.erick.mobilegame.Screens.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.erick.mobilegame.ViewModels.GameViewModel

@Composable
fun WordRow(guess: String, wordToGuess: String, showColors: Boolean, viewModel: GameViewModel) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(8.dp)
    ) {
        repeat(5) { index ->
            val letter = guess.getOrNull(index) ?: ' ' // Mostrar espacios vacíos cuando no hay letras
            val color = if (showColors && letter != ' ') {
                viewModel.getLetterColor(letter, index, wordToGuess) // Llamamos a la función del ViewModel
            } else {
                Color.White // Cuadros en blanco hasta que se envíe la palabra
            }

            LetterBox(letter = letter, backgroundColor = color)
        }
    }
}


