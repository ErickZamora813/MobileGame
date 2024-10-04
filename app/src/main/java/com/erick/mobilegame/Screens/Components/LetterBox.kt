package com.erick.mobilegame.Screens.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LetterBox(letter: Char, backgroundColor: Color) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .padding(8.dp)
            .background(backgroundColor)
            .border(3.dp, Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(text = letter.toString(), style = MaterialTheme.typography.headlineSmall, color = Color.Black)
    }

}