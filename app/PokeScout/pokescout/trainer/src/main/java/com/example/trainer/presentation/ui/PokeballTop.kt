package com.example.trainer.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.theme.PokeballRed

@Composable
fun PokeballTop() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(108.dp)
            .background(color = PokeballRed)
    )
}

@Preview
@Composable
fun PokeballTopPreview() {
    PokeballTop()
}