package com.example.leader.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.theme.PokeballWhite
import com.example.compose.theme.pokefontPixel

@Preview
@Composable
fun ScanScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Scaneaza un Tag...",
            fontSize = 16.sp,
            color = PokeballWhite,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}