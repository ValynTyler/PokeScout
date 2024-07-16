package com.example.trainer.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.themelibrary.PokeScoutTheme

@Composable
fun MainView() {
    PokeScoutTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Text(text = "Hello, PokeScout Trainer!")
        }
    }
}

@Preview
@Composable
fun MainViewPreview() {
    MainView()
}