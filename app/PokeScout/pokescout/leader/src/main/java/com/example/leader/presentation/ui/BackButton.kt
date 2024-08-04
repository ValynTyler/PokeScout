package com.example.leader.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.theme.PokeballWhite
import com.example.pokemon.presentation.theme.pokefontPixel

@Composable
fun BackButton(
    onClick: () -> Unit,
) {
    Box(
        Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            "<",
            fontSize = 50.sp,
            textAlign = TextAlign.Start,
            fontFamily = pokefontPixel,
            color = PokeballWhite,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .clickable { onClick() }
        )
    }
}