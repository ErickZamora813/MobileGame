package com.erick.mobilegame.Screens.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Keyboard(
    onKeyPressed: (Char) -> Unit,
    onDelete: () -> Unit,
    onEnter: () -> Unit,
    keyboardState: Map<Char, Color>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(4.dp)
        ) {
            "QWERTYUIOP".forEach { letter ->
                KeyboardKey(
                    letter = letter,
                    onKeyPressed = { onKeyPressed(letter) },
                    backgroundColor = keyboardState[letter] ?: Color.LightGray
                )
            }
        }


        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(4.dp)
        ) {
            "ASDFGHJKL".forEach { letter ->
                KeyboardKey(
                    letter = letter,
                    onKeyPressed = { onKeyPressed(letter) },
                    backgroundColor = keyboardState[letter] ?: Color.LightGray
                )
            }
        }


        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(4.dp)
        ) {

            KeyboardKey(
                text = "Borrar",
                onKeyPressed = { onDelete() },
                backgroundColor = Color.LightGray,
                modifier = Modifier.width(80.dp)
            )


            "ZXCVBNM".forEach { letter ->
                KeyboardKey(
                    letter = letter,
                    onKeyPressed = { onKeyPressed(letter) },
                    backgroundColor = keyboardState[letter] ?: Color.LightGray
                )
            }


            KeyboardKey(
                text = "Enter",
                onKeyPressed = { onEnter() },
                backgroundColor = Color.LightGray,
                modifier = Modifier.width(80.dp)
            )
        }
    }
}

@Composable
fun KeyboardKey(
    letter: Char? = null,
    text: String? = null,
    onKeyPressed: () -> Unit,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(20.dp)
            .height(30.dp)
            .background(backgroundColor)
            .clickable { onKeyPressed() }
            .border(
                BorderStroke(1.dp, Color.Black),
                shape = RoundedCornerShape(5.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (letter != null) {
            Text(text = letter.toString(), fontSize = 18.sp)
        } else if (text != null) {
            Text(text = text, fontSize = 14.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewKeyboard() {

    val simulatedKeyboardState = remember {
        mutableMapOf<Char, Color>().apply {
            ('A'..'Z').forEach { put(it, Color.LightGray) }
            this['A'] = Color.Green
            this['S'] = Color.Yellow
            this['D'] = Color.Gray
        }
    }

    Keyboard(
        onKeyPressed = {},
        onDelete = {},
        onEnter = {},
        keyboardState = simulatedKeyboardState
    )
}


