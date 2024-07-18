package com.example.trainer.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.compose.theme.PokeballGrey
import com.example.compose.theme.PokeballWhite

@Composable
fun PokeballButton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(108.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = 60.dp)
                .size(110.dp)
                .clip(CircleShape)
                .background(color = PokeballGrey)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = 45.dp)
                .size(75.dp)
                .clip(CircleShape)
                .background(color = PokeballWhite)
        )
    }
}