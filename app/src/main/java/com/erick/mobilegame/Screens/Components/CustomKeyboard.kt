package com.erick.mobilegame.Screens.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun Keyboard(
    onKeyPressed: (Char) -> Unit,
    onDelete: () -> Unit,
    onEnter: () -> Unit,
    keyboardState: Map<Char, androidx.compose.ui.graphics.Color>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val rows = listOf(
            "QWERTYUIOP",
            "ASDFGHJKL",
            "ZXCVBNM"
        )

        rows.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = androidx.compose.ui.Modifier.padding(4.dp)
            ) {
                row.forEach { letter ->
                    KeyboardKey(
                        letter = letter,
                        onKeyPressed = { onKeyPressed(letter) },
                        backgroundColor = keyboardState[letter]
                            ?: androidx.compose.ui.graphics.Color.White // Fondo blanco por defecto
                    )
                }
            }
        }

        // Fila de acciones (retroceso y Enter)
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = androidx.compose.ui.Modifier.padding(4.dp)
        ) {
            KeyboardKey(
                letter = '←',
                onKeyPressed = { onDelete() },
                backgroundColor = androidx.compose.ui.graphics.Color.LightGray
            )
            Spacer(modifier = androidx.compose.ui.Modifier.width(16.dp)) // Espacio entre los botones
            KeyboardKey(
                letter = '⏎',
                onKeyPressed = { onEnter() },
                backgroundColor = androidx.compose.ui.graphics.Color.LightGray
            )
        }
    }
}

@Composable
fun KeyboardKey(letter: Char, onKeyPressed: () -> Unit, backgroundColor: androidx.compose.ui.graphics.Color) {
    Box(
        modifier = androidx.compose.ui.Modifier
            .size(38.dp) // Tamaño más grande
            .padding(4.dp)
            .background(backgroundColor) // Colorear las teclas
            .border(2.dp, androidx.compose.ui.graphics.Color.Black) // Borde negro
            .clickable { onKeyPressed() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter.toString(),
            style = MaterialTheme.typography.bodyLarge, // Tamaño de texto más grande
            color = androidx.compose.ui.graphics.Color.Black // Color del texto en las teclas
        )
    }
}
